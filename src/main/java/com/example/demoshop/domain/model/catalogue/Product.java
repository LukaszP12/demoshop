package com.example.demoshop.domain.model.catalogue;

import com.example.demoshop.domain.model.common.Money;

import java.util.HashSet;
import java.util.Set;

public class Product {

    private final ProductId id;
    private final String name;
    private final Money price;
    private final int stock;
    private final Set<Category> categories = new HashSet<>();
    private final Set<ProductVariant> variants = new HashSet<>();
    private final Set<ProductReview> reviews = new HashSet<>();

    public Product(String name, Money price, int stock) {
        this.id = ProductId.newId();
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public void addVariant(ProductVariant variant) {
        variants.add(variant);
    }

    public Set<ProductVariant> variants() {
        return Set.copyOf(variants);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    public Set<Category> categories() {
        return Set.copyOf(categories);
    }

    public void addReview(ProductReview review) {
        reviews.add(review);
    }

    public Set<ProductReview> reviews() {
        return Set.copyOf(reviews);
    }

    public double averageRating() {
        return reviews.stream().mapToInt(ProductReview::rating).average().orElse(0);
    }
}
