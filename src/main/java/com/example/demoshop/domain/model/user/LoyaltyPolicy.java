package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user;

import java.math.BigDecimal;

public class LoyaltyPolicy {

    private final BigDecimal spendingPerPoint;

    public LoyaltyPolicy(BigDecimal spendingPerPoint) {
        if (spendingPerPoint.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Spending per point must be positive");
        }
        this.spendingPerPoint = spendingPerPoint;
    }

    public int calculatePoints(BigDecimal orderTotal) {
        if (orderTotal == null || orderTotal.compareTo(BigDecimal.ZERO) <= 0) {
            return 0;
        }
        return orderTotal.divide(spendingPerPoint, BigDecimal.ROUND_DOWN).intValue();
    }
}
