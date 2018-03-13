package com.noser.java.shopit.domain.price;

import com.noser.java.shopit.domain.product.Product;
import com.noser.java.shopit.domain.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Currency;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Service
public class SmartPriceService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmartPriceService.class);

    @Autowired
    private CompetitorService competitorService;

    @Autowired
    private ProductRepository productRepository;

    public SmartPrice find(String productId) {

        Product product =
                productRepository.findById(productId)
                                 .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found"));

        List<CompetitorPrice> competitorPrices =
                competitorService.findAll()
                                 .stream()
                                 .map(competitor -> competitor.getPrice(product))
                                 .flatMap(cf -> {
                                     try {
                                         return cf.get(5, TimeUnit.SECONDS)
                                                  .map(Stream::of)
                                                  .orElse(Stream.empty());
                                     } catch (Exception e) {
                                         LOGGER.warn("Exception while fetching price: {}", e.getMessage());
                                         return Stream.empty();
                                     }
                                 })
                                 .collect(toList());

        Currency chf = Currency.getInstance("CHF");

        Amount cheapestPrice = competitorPrices.stream()
                                               .map(CompetitorPrice::getAmount)
                                               .map(amount -> amount.convertTo(chf))
                                               .map(Amount::getDenominator)
                                               .min(BigDecimal::compareTo)
                                               .map(denominator -> denominator.multiply(new BigDecimal("0.95")))
                                               .map(denominator -> new Amount(denominator, chf))
                                               .orElseGet(() -> new Amount(new BigDecimal("10000"), chf));

        return new SmartPrice(UUID.randomUUID().toString(),
                              product,
                              cheapestPrice,
                              competitorPrices,
                              Instant.now().plusSeconds(3600));
    }

}
