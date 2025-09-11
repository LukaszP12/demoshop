package com.example.demoshop.domain.model.catalogue;

public class Category {

    private final String name;

    public Category(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }
        this.name = name;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
