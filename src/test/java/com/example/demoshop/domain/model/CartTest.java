package java.com.example.demoshop.domain.model;

import example.demoshop.domain.model.cart.Cart;
import example.demoshop.domain.model.cart.CartItem;
import example.demoshop.domain.model.catalogue.Product;
import example.demoshop.domain.model.common.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartTest {

    @Test
    void shouldCalculateTotalFromAllCartItems() {
        Cart cart = new Cart("1");

        Product mouse = new Product("SKU1", "Mouse", "", Money.of(new BigDecimal(50),"USD"),3);
        Product keyboard = new Product("SKU2", "Keyboard", "",Money.of(new BigDecimal(100),"USD"),5);

        cart.addItem(new CartItem(mouse, 2,Money.of(new BigDecimal(45),"USD")));     // 2 × 50 = 100
        cart.addItem(new CartItem(mouse, 2,Money.of(new BigDecimal(90),"USD")));  // 1 × 100 = 100

        Money total = cart.calculateTotal();

        assertEquals(BigDecimal.valueOf(200.00).setScale(2), total.getAmount());
    }
}
