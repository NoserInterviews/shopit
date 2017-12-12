package com.noser.java.shopit.domain.price;

import com.google.common.collect.ImmutableList;
import com.noser.java.shopit.domain.product.Product;
import com.noser.java.shopit.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CompetitorService {

    @Autowired
    private ProductRepository productRepository;

    private List<Competitor> competitors;

    @PostConstruct
    public void init() {

        competitors = new ArrayList<>();
        List<Product> products = productRepository.findAll();
        competitors.add(Competitor.random("zrack", "zrack",
                                          map(products, ImmutableList.of(new BigDecimal("10"),
                                                                         new BigDecimal("20"),
                                                                         new BigDecimal("7"),
                                                                         new BigDecimal("60"))),
                                          map(products, ImmutableList.of(new BigDecimal("2"),
                                                                         new BigDecimal("3"),
                                                                         new BigDecimal("1"),
                                                                         new BigDecimal("5")))));

        competitors.add(Competitor.random("anlog", "analog",
                                          map(products, ImmutableList.of(new BigDecimal("9"),
                                                                         new BigDecimal("19"),
                                                                         new BigDecimal("8"),
                                                                         new BigDecimal("57"))),
                                          map(products, ImmutableList.of(new BigDecimal("1"),
                                                                         new BigDecimal("3"),
                                                                         new BigDecimal("2"),
                                                                         new BigDecimal("4")))));

        competitors.add(Competitor.random("lefrai", "lefrai",
                                          map(products, ImmutableList.of(new BigDecimal("9.5"),
                                                                         new BigDecimal("18.7"),
                                                                         new BigDecimal("10.2"),
                                                                         new BigDecimal("47.30"))),
                                          map(products, ImmutableList.of(new BigDecimal("1.6"),
                                                                         new BigDecimal("2"),
                                                                         new BigDecimal("4"),
                                                                         new BigDecimal("13.9")))));

    }

    private Map<String, BigDecimal> map(List<Product> products, List<BigDecimal> decimals) {

        Map<String, BigDecimal> res = new HashMap<>();
        for (int i = 0; i < products.size(); i++) {
            res.put(products.get(i).getId(),
                    decimals.get(i));
        }
        return res;
    }

    public List<Competitor> findAll() {

        return new ArrayList<>(competitors);
    }
}
