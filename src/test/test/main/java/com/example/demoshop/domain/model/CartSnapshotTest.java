package main.java.com.example.demoshop.domain.model;

import com.example.demoshop.domain.model.common.Money;
import com.example.demoshop.domain.model.cart.Cart;
import com.example.demoshop.domain.model.cart.CartSnapshot;
import com.example.demoshop.domain.model.catalogue.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartSnapshotTest {

    @Test
    void shouldSaveAndRestoreCart() {
        Product product1 = new Product("Laptop","DELL Laptop", Money.of(1000.00), 10);
        Product product2 = new Product("Mouse","PC Mouse", Money.of(50.00), 20);

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
