package com.noser.java.shopit.domain.price;

import com.noser.java.shopit.domain.product.Product;

import java.time.Instant;
import java.util.List;

public class SmartPrice {

    private String id;
    private Product product;
    private Amount price;
    private List<CompetitorPrice> competitionPrices;
    private Instant validUntil;

    public SmartPrice(String id,
                      Product product,
                      Amount price,
                      List<CompetitorPrice> competitionPrices, Instant validUntil) {

        this.id = id;
        this.product = product;
        this.price = price;
        this.competitionPrices = competitionPrices;
        this.validUntil = validUntil;
    }

    public String getId() {

        return id;
    }

    public Product getProduct() {

        return product;
    }

    public Amount getPrice() {

        return price;
    }

    public List<CompetitorPrice> getCompetitionPrices() {

        return competitionPrices;
    }

    public Instant getValidUntil() {

        return validUntil;
    }
}
