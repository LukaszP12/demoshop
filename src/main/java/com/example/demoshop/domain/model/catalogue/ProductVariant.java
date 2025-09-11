package com.example.demoshop.domain.model.catalogue;

import java.util.Objects;

public class ProductVariant {

    private final String color;
    private final String size;

    public ProductVariant(String color, String size) {
        this.color = color;
        this.size = size;
    }

    public String color() { return color; }
    public String size() { return size; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductVariant)) return false;
        ProductVariant that = (ProductVariant) o;
        return Objects.equals(color, that.color) && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, size);
    }
}
