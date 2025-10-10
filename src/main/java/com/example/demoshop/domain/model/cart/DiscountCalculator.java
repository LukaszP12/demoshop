package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.cart;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;

import java.math.BigDecimal;

public class DiscountCalculator {

    public Money calculateDiscount(Cart cart) {
        Money total = cart.total();

        if (total.isGreaterThan(Money.of(BigDecimal.valueOf(1000.00), "USD"))) {
            return total.multiply(BigDecimal.valueOf(0.10)); // 10% discount
        }

        if (cart.totalItems() >= 5) {
            return total.multiply(BigDecimal.valueOf(0.05)); // 5% discount
        }

        return Money.zero("USD");
    }
}
