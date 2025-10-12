package main.java.com.example.demoshop.domain.model;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.common.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {

    @Test
    void shouldConvertAmountToTargetCurrencyUsingGivenRate() {
        Money pln = Money.of(BigDecimal.valueOf(100.00), "" + Currency.getInstance("PLN"));

        Money eur = pln.convertTo(Currency.getInstance("EUR"), BigDecimal.valueOf(0.22));

        assertEquals(BigDecimal.valueOf(22.00).setScale(2), eur.getAmount());
        assertEquals("EUR", eur.getCurrency());
    }

    @Test
    void shouldRoundToTwoDecimalPlaces() {
        Money pln = Money.of(BigDecimal.valueOf(100.00), "" + Currency.getInstance("PLN"));

        Money usd = pln.convertTo(Currency.getInstance("USD"), BigDecimal.valueOf(0.2567));

        assertEquals(BigDecimal.valueOf(25.67).setScale(2), usd.getAmount());
    }

}
