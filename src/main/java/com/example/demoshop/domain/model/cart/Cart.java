package com.example.demoshop.domain.model.cart;

import com.example.demoshop.domain.model.common.Money;
import com.example.demoshop.domain.model.catalogue.Product;

import java.util.*;

public class Cart {

    private final CartId id;
    private final String userId;
    private final Map<Product.ProductId, CartItem> items = new HashMap<>();

    public Cart(String userId) {
        this.id = CartId.newId();
        this.userId = userId;
    }

    public CartId id() { return id; }
    public String userId() { return userId; }
    public Collection<CartItem> items() { return items.values(); }

    public void addProduct(Product product, int quantity) {
        CartItem item = items.get(product.id());
        if (item != null) {
            item.increaseQuantity(quantity);
        } else {
            items.put(product.id(), new CartItem(product, quantity));
        }
    }

    public void removeProduct(Product.ProductId productId) {
        items.remove(productId);
    }

    public Money total() {
        return items.values().stream()
                .map(CartItem::subtotal)
                .reduce(Money.zero(), Money::add);
    }
}
