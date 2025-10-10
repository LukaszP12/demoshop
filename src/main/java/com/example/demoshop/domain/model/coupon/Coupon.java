package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.coupon;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document(collection = "coupons")
public class Coupon {

    @Id
    private String id;
    private String code;              // e.g. "WELCOME10"
    private BigDecimal discountValue; // either percentage or fixed amount
    private boolean percentage;       // true = percentage, false = fixed
    private final LocalDate expiryDate;
    private int usageLimit;           // max number of uses
    private int usedCount;            // how many times used so far

    public Coupon(String code, BigDecimal discountValue, boolean percentage,
                  LocalDate expiryDate, int usageLimit) {
        this.code = code;
        this.discountValue = discountValue;
        this.percentage = percentage;
        this.expiryDate = expiryDate;
        this.usageLimit = usageLimit;
        this.usedCount = 0;
    }

    public boolean isValid() {
        return LocalDate.now().isBefore(expiryDate.plusDays(1))
                && usedCount < usageLimit;
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

    public Money applyToMoney(Money amount) {
        if (!isValid()) {
            throw new IllegalStateException("Coupon is invalid or expired");
        }

        Money discount;

        if (percentage) {
            // discountValue is a BigDecimal like 10 for 10%
            BigDecimal fraction = discountValue.divide(BigDecimal.valueOf(100));
            discount = amount.multiply(fraction);
        } else {
            // fixed amount discount
            discount = Money.of(discountValue, amount.getCurrency());
        }

        Money totalAfterDiscount = amount.subtract(discount);

        // prevent negative totals
        if (totalAfterDiscount.isNegative()) {
            return Money.of(BigDecimal.ZERO, amount.getCurrency());
        }

        return totalAfterDiscount;
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

    public LocalDate getExpiryDate() { return expiryDate; }

    public int getUsageLimit() {
        return usageLimit;
    }

    public int getUsedCount() {
        return usedCount;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "discountValue=" + discountValue +
                ", percentage=" + percentage +
                ", expiryDate=" + expiryDate +
                ", usageLimit=" + usageLimit +
                ", usedCount=" + usedCount +
                '}';
    }
}
