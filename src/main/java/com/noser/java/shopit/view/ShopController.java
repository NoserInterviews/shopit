package com.noser.java.shopit.view;

import com.noser.java.shopit.domain.product.Category;
import com.noser.java.shopit.domain.product.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class ShopController {

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping("/shop")
    public String shop(@RequestParam(name = "selected", required = false) String selected,
                       Model model) {

        Optional<Category> optCategory = categoryRepository.findByName(selected);

        model.addAttribute("categories", categoryRepository.getTopLevel());
        model.addAttribute("selectedCategory", null);
        return "shop";
    }
}
