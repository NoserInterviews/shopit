package com.noser.java.shopit.domain.warehouse;

import com.noser.java.shopit.domain.price.Amount;
import com.noser.java.shopit.domain.product.Product;

import java.time.LocalDate;

public class Stock {

    private Product product;
    private Amount orderPrice;
    private LocalDate orderDate;
    private int orderAmount;
    private LocalDate deliveryDate;
    private int deliveredAmount;
    private Amount productPrice;
    private int remainingAmount;
}
