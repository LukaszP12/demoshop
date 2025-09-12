package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "coupons")
public class Coupon {

    @Id
    private String id;
    private String code;              // e.g. "WELCOME10"
    private BigDecimal discountValue; // either percentage or fixed amount
    private boolean percentage;       // true = percentage, false = fixed
    private Instant expiryDate;
    private int usageLimit;           // max number of uses
    private int usedCount;            // how many times used so far

    public Coupon(String code, BigDecimal discountValue, boolean percentage,
                  Instant expiryDate, int usageLimit) {
        this.code = code;
        this.discountValue = discountValue;
        this.percentage = percentage;
        this.expiryDate = expiryDate;
        this.usageLimit = usageLimit;
        this.usedCount = 0;
    }

    public boolean isValid() {
        return Instant.now().isBefore(expiryDate) && usedCount < usageLimit;
    }

    public BigDecimal apply(BigDecimal orderTotal) {
        if (!isValid()) {
            throw new IllegalStateException("Coupon is invalid or expired");
        }

        BigDecimal discount = percentage
                ? orderTotal.multiply(discountValue).divide(BigDecimal.valueOf(100))
                : discountValue;

        return orderTotal.subtract(discount).max(BigDecimal.ZERO);
    }

    public void incrementUsage() {
        if (usedCount >= usageLimit) {
            throw new IllegalStateException("Coupon usage limit exceeded");
        }
        this.usedCount++;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public int getUsageLimit() {
        return usageLimit;
    }

    public int getUsedCount() {
        return usedCount;
    }
}
