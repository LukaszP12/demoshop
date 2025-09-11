package main.java.com.example.demoshop.domain.model;

import com.example.demoshop.domain.model.catalogue.Product;
import com.example.demoshop.domain.model.common.Money;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    @Test
    void shouldIncreaseStock() {
        Product product = new Product("Laptop","DELL Laptop", Money.of(1000.00), 10);
        product.increaseStock(5);

        assertEquals(15, product.stockQuantity());
    }

    @Test
    void shouldThrowWhenIncreasingNegativeOrZero() {
        Product product = new Product("Laptop","DELL Laptop", Money.of(1000.00), 10);

        assertThrows(IllegalArgumentException.class, () -> product.increaseStock(0));
        assertThrows(IllegalArgumentException.class, () -> product.increaseStock(-5));
    }

    @Test
    void shouldDecreaseStock() {
        Product product = new Product("Laptop","DELL Laptop", Money.of(1000.00), 10);
        product.decreaseStock(4);

        assertEquals(6, product.stockQuantity());
    }


    @Test
    void shouldThrowWhenDecreasingNegativeOrZero() {
        Product product = new Product("Laptop","DELL Laptop", Money.of(1000.00), 10);

        assertThrows(IllegalArgumentException.class, () -> product.decreaseStock(0));
        assertThrows(IllegalArgumentException.class, () -> product.decreaseStock(-3));
    }

    @Test
    void shouldThrowWhenDecreasingMoreThanStock() {
        Product product = new Product("Laptop","DELL Laptop", Money.of(1000.00), 10);

        assertThrows(IllegalStateException.class, () -> product.decreaseStock(15));
    }
}
