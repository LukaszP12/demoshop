package com.example.demoshop.domain.model;

import example.demoshop.domain.model.warehouse.Warehouse;
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

    @Test
    void shouldReserveStockForProduct() {
        Warehouse warehouse = new Warehouse("Main Warehouse");
        warehouse.restock("P123", 10);

        warehouse.reserve("P123", 4);

        assertEquals(6, warehouse.getAvailableQuantity("P123"));
        assertEquals(4, warehouse.getReservedQuantity("P123"));
    }

    @Test
    void shouldShipReservedStock() {
        Warehouse warehouse = new Warehouse("Main Warehouse");
        warehouse.restock("P123", 10);
        warehouse.reserve("P123", 5);

        warehouse.ship("P123", 5);

        assertEquals(5, warehouse.getAvailableQuantity("P123"));
        assertEquals(0, warehouse.getReservedQuantity("P123"));
    }
}
