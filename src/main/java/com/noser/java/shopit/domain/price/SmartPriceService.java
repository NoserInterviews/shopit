package com.noser.java.shopit.domain.price;

import com.noser.java.shopit.domain.warehouse.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmartPriceService {

    @Autowired
    private Warehouse warehouse;

    public SmartPrice find(String productId) {

        throw new UnsupportedOperationException();
    }

}
