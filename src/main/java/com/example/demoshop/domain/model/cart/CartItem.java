package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.Product;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.catalogue.ProductId;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;

import java.math.BigDecimal;
import java.util.Objects;

public class CartItem {

    private final ProductId productId;
    private final Money unitPrice;
    private int quantity;

    public CartItem(Product product, int quantity, Money unitPrice) {
        if (quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");
        this.productId = product.getId();
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public ProductId productId() { return productId; }
    public Money unitPrice() { return unitPrice; }
    public int quantity() { return quantity; }

    public void increaseQuantity(int amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        this.quantity += amount;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Money subtotal() {
        return unitPrice.multiply(new BigDecimal(quantity));
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
