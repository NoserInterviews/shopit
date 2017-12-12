package com.noser.java.shopit.domain.product;

import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoryRepository {

    private Category root;
    private List<Category> topLevel;

    @PostConstruct
    public void init() {

        topLevel = new ArrayList<>();

        topLevel.add(new Category("Sauger",
                                  "Sauger",
                                  "Geräte die Luft einsaugen",
                                  new Category("Industriestaubsauger",
                                               "Industriestaubsauger",
                                               "Besonders leistungsfähig"),
                                  new Category("Staubsauger",
                                               "Staubsauger",
                                               "Für private Haushalte geeignet")));

        topLevel.add(new Category("Mixer",
                                  "Mixer",
                                  "Hier wird gemixt",
                                  new Category("Standmixer",
                                               "Standgeräte",
                                               "Besonders leistungsfähig"),
                                  new Category("Stabmixer",
                                               "Stabmixer",
                                               "Die handliche Alternative")));

        root = new Category("not used",
                            "not used",
                            "not used",
                            topLevel.toArray(new Category[topLevel.size()]));
    }


    public List<Category> getTopLevel() {

        return Collections.unmodifiableList(topLevel);
    }

    public Optional<Category> findByName(String name) {

        return Optional.ofNullable(name)
                       .flatMap(aName -> findByNameRecursive(name, root));
    }

    private Optional<Category> findByNameRecursive(@Nonnull String name,
                                                   @Nonnull Category category) {

        if (category.getName().equals(name)) {
            return Optional.of(category);
        } else {
            for (Category subCategory : category.getSubCategories()) {
                Optional<Category> subResult = findByNameRecursive(name, subCategory);
                if (subResult.isPresent()) return subResult;
            }
        }
        return Optional.empty();
    }
}
