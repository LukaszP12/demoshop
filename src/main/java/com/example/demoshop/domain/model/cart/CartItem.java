package com.example.demoshop.domain.model.cart;

import com.example.demoshop.domain.model.common.Money;
import com.example.demoshop.domain.model.catalogue.Product;

import java.util.Objects;

public class CartItem {

    private final Product.ProductId productId;
    private final String productName;
    private final Money unitPrice;
    private int quantity;

    public CartItem(Product product, int quantity) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        this.productId = product.id();
        this.productName = product.name();
        this.unitPrice = product.price();
        this.quantity = quantity;
    }

    public Product.ProductId productId() { return productId; }
    public String productName() { return productName; }
    public Money unitPrice() { return unitPrice; }
    public int quantity() { return quantity; }

    public void increaseQuantity(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.quantity += amount;
    }

    public Money subtotal() {
        return unitPrice.multiply(quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return productId.equals(cartItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
