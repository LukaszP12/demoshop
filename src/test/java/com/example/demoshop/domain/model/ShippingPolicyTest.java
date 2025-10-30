package java.com.example.demoshop.domain.model;

import main.java.com.example.demoshop.java.com.example.demoshop.application.cart.ShippingPolicy;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShippingPolicyTest {

    private final Currency PLN = Currency.getInstance("PLN");

    @Test
    void shouldApplyFreeShippingWhenThresholdReached() {
        ShippingPolicy policy = new ShippingPolicy(
                BigDecimal.valueOf(200),    // threshold
                BigDecimal.valueOf(15),     // normal fee
                PLN
        );

        Money shipping = policy.calculateShipping(Money.of(BigDecimal.valueOf(250), String.valueOf(PLN)));

        assertEquals(BigDecimal.ZERO.setScale(2), shipping.getAmount());
    }

    @Test
    void shouldApplyStandardFeeWhenBelowThreshold() {
        ShippingPolicy policy = new ShippingPolicy(
                BigDecimal.valueOf(200),
                BigDecimal.valueOf(15),
                PLN
        );

        Money shipping = policy.calculateShipping(Money.of(BigDecimal.valueOf(150), String.valueOf(PLN)));

        assertEquals(BigDecimal.valueOf(15.00).setScale(2), shipping.getAmount());
    }
}
