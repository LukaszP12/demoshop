package com.example.demoshop.domain.model;
import example.demoshop.domain.model.cart.Cart;
import example.demoshop.domain.model.cart.CartSnapshot;
import example.demoshop.domain.model.catalogue.Product;
import example.demoshop.domain.model.common.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartSnapshotTest {

    @Test
    void shouldSaveAndRestoreCart() {
        Product product1 = new Product(
                "P1",
                "Laptop",
                "DELL Laptop",
                Money.of(BigDecimal.valueOf(1000.00), "USD"),
                10
        );
        Product product2 = new Product(
                "P2",
                "Mouse",
                "PC Mouse",
                Money.of(BigDecimal.valueOf(50.00), "USD"),
                20
        );

        Cart cart = new Cart("user1");
        cart.addProduct(product1, 1);
        cart.addProduct(product2, 2);

        // Save snapshot
        CartSnapshot snapshot = new CartSnapshot(cart);

        // Restore cart
        Cart restoredCart = Cart.fromSnapshot(snapshot);

        // Verify restored cart
        assertEquals(cart.userId(), restoredCart.userId());
        assertEquals(cart.items().size(), restoredCart.items().size());
        assertEquals(cart.total(), restoredCart.total());
        assertEquals(cart.lastUpdated(), restoredCart.lastUpdated());
    }
}
