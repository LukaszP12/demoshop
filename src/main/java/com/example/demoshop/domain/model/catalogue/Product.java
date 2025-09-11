package com.example.demoshop.domain.model.catalogue;

import com.example.demoshop.domain.model.common.Money;

import java.util.Objects;
import java.util.UUID;

public class Product {

    private final ProductId id;
    private String name;
    private String description;
    private Money price;
    private int stockQuantity;

    public Product(String name, String description, Money price, int stockQuantity) {
        this.id = ProductId.newId();
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.description = Objects.requireNonNull(description, "Description cannot be null");
        this.price = Objects.requireNonNull(price, "Price cannot be null");
        if (stockQuantity < 0) throw new IllegalArgumentException("Stock cannot be negative");
        this.stockQuantity = stockQuantity;
    }

    // Getters
    public ProductId id() { return id; }
    public String name() { return name; }
    public String description() { return description; }
    public Money price() { return price; }
    public int stockQuantity() { return stockQuantity; }

    // Domain behavior
    public void changePrice(Money newPrice) {
        if (newPrice == null || newPrice.isZeroOrNegative()) {
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = newPrice;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Increase must be positive");
        this.stockQuantity += quantity;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Decrease must be positive");
        if (quantity > this.stockQuantity) throw new IllegalStateException("Not enough stock available");
        this.stockQuantity -= quantity;
    }

    // Value Object for ProductId
    public static class ProductId {
        private final String value;

        private ProductId(String value) { this.value = value; }

        public static ProductId newId() { return new ProductId(UUID.randomUUID().toString()); }

        public String value() { return value; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ProductId)) return false;
            ProductId that = (ProductId) o;
            return value.equals(that.value);
        }

        @Override
        public int hashCode() { return value.hashCode(); }
    }
}
