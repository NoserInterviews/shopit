package com.noser.java.shopit.domain.product;

import org.springframework.stereotype.Repository;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class CategoryRepository {

    private Category root;
    private List<Category> topLevel;

    @PostConstruct
    public void init() {

        topLevel = new ArrayList<>();

        topLevel.add(new Category(UUID.randomUUID().toString(),
                                  "Sauger",
                                  "Geräte die Luft einsaugen",
                                  new Category(UUID.randomUUID().toString(),
                                               "Industriestaubsauger",
                                               "Besonders leistungsfähig"),
                                  new Category((UUID.randomUUID().toString()),
                                               "Staubsauger",
                                               "Für private Haushalte geeignet")));

        topLevel.add(new Category(UUID.randomUUID().toString(),
                                  "Mixer",
                                  "Hier wird gemixt",
                                  new Category(UUID.randomUUID().toString(),
                                               "Standgeräte",
                                               "Besonders leistungsfähig"),
                                  new Category((UUID.randomUUID().toString()),
                                               "Stabmixer",
                                               "Die handliche Alternatives")));

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
