package com.noser.java.shopit.view;

import com.noser.java.shopit.domain.product.CategoryRepository;
import com.noser.java.shopit.domain.product.Product;
import com.noser.java.shopit.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class ShopController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @RequestMapping("/shop")
    public String shop(@RequestParam(name = "selected", required = false) String selected,
                       Model model) {

        List<Product> products = categoryRepository.findByName(selected)
                                                   .map(productRepository::findByCategory)
                                                   .orElseGet(Collections::emptyList);

        model.addAttribute("categories", categoryRepository.getTopLevel());
        model.addAttribute("products", products);
        return "shop";
    }
}
