package com.noser.java.shopit.domain.price;

import com.noser.java.shopit.domain.product.Product;

import java.time.Instant;
import java.util.Map;

public class SmartPrice {

    private String id;
    private Product product;
    private Amount price;
    private Map<String, Amount> competitionPrices;
    private Instant validUntil;

}
