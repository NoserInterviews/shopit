package com.noser.java.shopit.util;

import java.util.function.Supplier;

/**
 * NOT threadsafe
 */
public class Lazy<A> {

    private A value;
    private final Supplier<A> supplier;

    private Lazy(Supplier<A> supplier) {

        this.supplier = supplier;
    }

    public static <U> Lazy<U> of(Supplier<U> supplier) {

        return new Lazy<>(supplier);
    }

    public A get() {

        if (value == null) {
            value = supplier.get();
        }
        return value;
    }
}
