package com.noser.java.shopit.domain.product;

public class Product {

    private final String id;
    private final String name;
    private final String description;
    private final String[] categoryIds;

    public Product(String id, String name, String description, String[] categoryIds) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryIds = categoryIds;
    }

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public String getDescription() {

        return description;
    }

    public String[] getCategoryIds() {

        return categoryIds;
    }
}
