package com.noser.java.shopit.domain.price;

public class CompetitorPrice {

    private String competitorId;
    private String competitorName;
    private String productId;
    private Amount amount;

    public CompetitorPrice(String competitorId,
                           String competitorName,
                           String productId,
                           Amount amount) {

        this.competitorId = competitorId;
        this.competitorName = competitorName;
        this.productId = productId;
        this.amount = amount;
    }

    public String getCompetitorId() {

        return competitorId;
    }

    public String getCompetitorName() {

        return competitorName;
    }

    public String getProductId() {

        return productId;
    }

    public Amount getAmount() {

        return amount;
    }
}