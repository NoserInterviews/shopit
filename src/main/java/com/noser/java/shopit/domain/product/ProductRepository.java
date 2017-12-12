package com.noser.java.shopit.domain.product;

import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Repository
public class ProductRepository {

    private Map<String, Product> products;

    @PostConstruct
    public void init() {

        List<Product> list = new ArrayList<>();
        list.add(new Product(UUID.randomUUID().toString(),
                             "HAL-9000",
                             "Eiskalt",
                             new String[]{"Industriestaubsauger" }));
        list.add(new Product(UUID.randomUUID().toString(),
                             "Milex 3",
                             "Heissss",
                             new String[]{"Staubsauger" }));
        list.add(new Product(UUID.randomUUID().toString(),
                             "Bababamix",
                             "Schnell",
                             new String[]{"Stabmixer" }));
        list.add(new Product(UUID.randomUUID().toString(),
                             "Jack LeLane Power Juicer",
                             "Sehr cool und fruchtig",
                             new String[]{"Standmixer" }));

        products = new HashMap<>();
        for (Product product : list) {
            products.put(product.getId(), product);
        }
    }

    public List<Product> findByCategory(@Nonnull Category category) {

        Set<String> categoryIds = new HashSet<>();
        findAllCategoryIdsRecursive(categoryIds, category);

        return products.values()
                       .stream()
                       .filter(product -> Stream.of(product.getCategoryIds())
                                                .anyMatch(categoryIds::contains))
                       .collect(toList());
    }

    private void findAllCategoryIdsRecursive(@Nonnull Set<String> inCategoryIds,
                                             @Nonnull Category category) {

        inCategoryIds.add(category.getId());
        for (Category subCategory : category.getSubCategories()) {
            findAllCategoryIdsRecursive(inCategoryIds, subCategory);
        }
    }

    public List<Product> findAll() {

        return new ArrayList<>(products.values());
    }
}
