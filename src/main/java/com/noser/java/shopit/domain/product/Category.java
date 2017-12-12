package com.noser.java.shopit.domain.product;

public class Category {

    private final String id;
    private final String name;
    private final String description;
    private final Category[] subCategories;

    public Category(String id,
                    String name,
                    String description,
                    Category... subCategories) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.subCategories = subCategories;
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

    public Category[] getSubCategories() {

        return subCategories;
    }
}
