package main.java.com.example.demoshop.java.com.example.demoshop.application.cart;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;

import java.math.BigDecimal;
import java.util.Currency;

public class ShippingPolicy {

    private final BigDecimal freeShippingThreshold;
    private final BigDecimal standardShippingFee;
    private final Currency currency;

    public ShippingPolicy(BigDecimal freeShippingThreshold, BigDecimal standardShippingFee, Currency currency) {
        this.freeShippingThreshold = freeShippingThreshold;
        this.standardShippingFee = standardShippingFee;
        this.currency = currency;
    }

    public Money calculateShipping(Money cartTotal) {
        if (cartTotal.getAmount().compareTo(freeShippingThreshold) >= 0) {
            return Money.of(BigDecimal.ZERO, String.valueOf(currency));
        }
        return  Money.of(standardShippingFee, String.valueOf(currency));
    }
}
