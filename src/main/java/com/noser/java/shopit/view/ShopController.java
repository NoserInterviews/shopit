package com.noser.java.shopit.view;

import com.noser.java.shopit.domain.price.SmartPrice;
import com.noser.java.shopit.domain.price.SmartPriceService;
import com.noser.java.shopit.domain.product.CategoryRepository;
import com.noser.java.shopit.domain.product.Product;
import com.noser.java.shopit.domain.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Controller
public class ShopController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SmartPriceService smartPriceService;

    @RequestMapping("/shop")
    public String shop(@RequestParam(name = "selected", required = false) String selected,
                       Model model) {

        List<Product> products = categoryRepository.findByName(selected)
                                                   .map(productRepository::findByCategory)
                                                   .orElseGet(productRepository::findAll);

        Map<String, SmartPrice> prices = products.stream()
                                                  .map(Product::getId)
                                                  .collect(toMap(Function.identity(),
                                                                 smartPriceService::find));


        model.addAttribute("categories", categoryRepository.getTopLevel());
        model.addAttribute("products", products);
        model.addAttribute("prices", prices);
        return "shop";
    }
}
