package com.example.demoshop.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WarehouseTest {

    @Test
    void shouldIncreaseStockWhenRestocked() {
        // given
        Warehouse warehouse = new Warehouse("Main Warehouse");
        // when
        warehouse.restock("P123", 10);
        // then
        assertEquals(10, warehouse.getAvailableQuantity("P123"));
    }
}
