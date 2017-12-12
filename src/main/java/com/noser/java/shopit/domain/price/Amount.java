package com.noser.java.shopit.domain.price;

import java.math.BigDecimal;
import java.util.Currency;

public class Amount implements Comparable<Amount> {

    private final BigDecimal denominator;
    private final Currency currency;

    public Amount(BigDecimal denominator, Currency currency) {

        this.denominator = denominator;
        this.currency = currency;
    }

    public BigDecimal getDenominator() {

        return denominator;
    }

    public Currency getCurrency() {

        return currency;
    }

    @Override
    public int compareTo(Amount o) {

        return 0;
    }

    public Amount convertTo(Currency chf) {
        if (chf.equals(currency)) {
            return this;
        } else {
            throw new UnsupportedOperationException("not yet implemented");
        }
    }

    @Override
    public String toString() {

        return "[" + denominator +
                " " + currency +
                ']';
    }
}
