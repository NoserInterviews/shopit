package com.noser.java.shopit.domain.price;

import com.noser.java.shopit.domain.product.Product;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Mock implementation of standardized competitor API access (imagine having an
 * adapter per competitor)
 */
public class Competitor {

    private static final Random RANDOM = new Random();
    private static final ScheduledExecutorService EXECUTOR = Executors.newSingleThreadScheduledExecutor();
    private static final double PRODUCT_AVAILABLE_PROB = 0.9;
    private static final double CONNECTION_PROBLEM_PROB = 0.05;

    private String id;
    private String name;
    private Function<Product, CompletableFuture<Optional<CompetitorPrice>>> priceFetcher;

    private Competitor(String id,
                       String name,
                       Function<Product, CompletableFuture<Optional<CompetitorPrice>>> priceFetcher) {

        this.id = id;
        this.name = name;
        this.priceFetcher = priceFetcher;
    }

    public static Competitor random(@Nonnull String id,
                                    @Nonnull String name,
                                    @Nonnull Map<String, BigDecimal> means,
                                    @Nonnull Map<String, BigDecimal> stdDevs) {

        Set<String> notAvailableProducts = new HashSet<>();
        for (String productId : means.keySet()) {
            if (RANDOM.nextDouble() > PRODUCT_AVAILABLE_PROB) {
                notAvailableProducts.add(productId);
            }
        }

        return new Competitor(id,
                              name,
                              product -> {
                                  CompletableFuture<Optional<CompetitorPrice>> res = new CompletableFuture<>();
                                  if (RANDOM.nextDouble() < CONNECTION_PROBLEM_PROB) {
                                      EXECUTOR.schedule(() -> {
                                          res.completeExceptionally(new SocketException("Connection reset by peer"));
                                      }, delay(100L, 100L), TimeUnit.MILLISECONDS);
                                  } else {
                                      EXECUTOR.schedule(() -> {
                                          if (notAvailableProducts.contains(product.getId())) {
                                              res.complete(Optional.empty());
                                          } else {
                                              BigDecimal decimal = means.get(product.getId())
                                                                        .add(stdDevs.get(product.getId())
                                                                                    .multiply(BigDecimal.valueOf(RANDOM.nextGaussian())))
                                                                        .max(BigDecimal.ONE);
                                              res.complete(Optional.of(new CompetitorPrice(id,
                                                                                           name,
                                                                                           product.getId(),
                                                                                           new Amount(decimal,
                                                                                                      Currency.getInstance(
                                                                                                              "CHF")))));
                                          }
                                      }, delay(100L, 100L), TimeUnit.MILLISECONDS);
                                  }
                                  return res;
                              });
    }

    private static long delay(long peak, long stdDev) {

        return Math.max(0L, peak + (long) (RANDOM.nextGaussian() * stdDev + 0.5));
    }

    public CompletableFuture<Optional<CompetitorPrice>> getPrice(Product product) {

        return priceFetcher.apply(product);
    }

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }
}
