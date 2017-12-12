package com.noser.java.shopit.domain.price;

import java.math.BigDecimal;
import java.util.Currency;

public class Amount {

    private final BigDecimal amount;
    private final Currency currency;

    public Amount(BigDecimal amount, Currency currency) {

        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {

        return amount;
    }

    public Currency getCurrency() {

        return currency;
    }
}
