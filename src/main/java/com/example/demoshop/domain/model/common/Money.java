package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common;


import java.math.BigDecimal;
import java.util.Objects;

public final class Money {

    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money of(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money multiply(int factor) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(factor)));
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Money applyDiscount(BigDecimal discountValue, boolean percentage) {
        BigDecimal discount = percentage
                ? this.amount.multiply(discountValue).divide(BigDecimal.valueOf(100))
                : discountValue;

        BigDecimal newAmount = this.amount.subtract(discount).max(BigDecimal.ZERO);
        return new Money(newAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    @Override
    public String toString() {
        return amount.toPlainString();
    }

    public boolean isZeroOrNegative() {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }
}
