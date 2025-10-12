package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public final class Money implements Comparable<Money> {
    private final BigDecimal amount;
    private final String currency;

    private Money(BigDecimal amount, String currency) {
        if (amount == null || currency == null) {
            throw new IllegalArgumentException("Amount and currency cannot be null");
        }
        this.amount = amount.setScale(2, RoundingMode.HALF_UP); // enforce cents precision
        this.currency = currency;
    }

    public static Money of(BigDecimal amount, String currency) {
        return new Money(amount, currency);
    }

    public static Money from(Money other) {
        if (other == null) {
            throw new IllegalArgumentException("Money to copy cannot be null");
        }
        return new Money(other.amount, other.getCurrency());
    }

    public static Money zero(String currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public Money add(Money other) {
        checkCurrency(other);
        return new Money(this.amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        checkCurrency(other);
        return new Money(this.amount.subtract(other.amount), currency);
    }

    public Money multiply(BigDecimal factor) {
        return new Money(this.amount.multiply(factor), currency);
    }

    private void checkCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch: " + this.currency + " vs " + other.currency);
        }
    }

    public Money convertTo(Currency targetCurrency, BigDecimal rate) {
        BigDecimal converted = this.amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
        return new Money(converted, targetCurrency.getCurrencyCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;
        return amount.equals(money.amount) && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return amount + " " + currency;
    }

    private void ensureSameCurrency(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch: " + this.currency + " vs " + other.currency);
        }
    }

    @Override
    public int compareTo(Money other) {
        ensureSameCurrency(other);
        return amount.compareTo(other.amount);
    }

    public boolean isGreaterThan(Money other) {
        return compareTo(other) > 0;
    }
}
