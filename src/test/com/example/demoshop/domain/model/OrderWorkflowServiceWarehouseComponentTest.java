package com.example.demoshop.domain.model;

import example.demoshop.application.order.OrderEventPublisher;
import example.demoshop.application.order.OrderWorkflowService;
import example.demoshop.application.shipping.ShippingEventPublisher;
import example.demoshop.application.shipping.ShippingService;
import example.demoshop.domain.model.common.Money;
import example.demoshop.domain.model.order.Order;
import example.demoshop.domain.model.order.OrderItem;
import example.demoshop.domain.model.warehouse.Warehouse;
import example.demoshop.domain.repository.CartRepository;
import example.demoshop.domain.repository.CouponRepository;
import example.demoshop.domain.repository.OrderRepository;
import example.demoshop.domain.repository.ProductRepository;
import example.demoshop.domain.repository.ShipmentRepository;
import example.demoshop.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OrderWorkflowServiceWarehouseComponentTest {

    private OrderWorkflowService orderWorkflowService;
    private Warehouse warehouse;

    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private ShippingService shippingService;
    private ShipmentRepository shipmentRepository;
    private CouponRepository couponRepository;
    private UserRepository userRepository;
    private OrderEventPublisher orderEventPublisher;
    private ShippingEventPublisher shippingEventPublisher;
    private WarehouseRepository warehouseRepository;

    @BeforeEach
    void setUp() {
        warehouse = new Warehouse("Main Warehouse");
        warehouse.restock("P123", 10);

        orderRepository = mock(OrderRepository.class);
        cartRepository = mock(CartRepository.class);
        productRepository = mock(ProductRepository.class);
        shippingService = mock(ShippingService.class);
        shipmentRepository = mock(ShipmentRepository.class);
        couponRepository = mock(CouponRepository.class);
        userRepository = mock(UserRepository.class);
        orderEventPublisher = mock(OrderEventPublisher.class);
        shippingEventPublisher = mock(ShippingEventPublisher.class);
        warehouseRepository = mock(WarehouseRepository.class);

        when(warehouseRepository.findDefault()).thenReturn(warehouse);

        orderWorkflowService = new OrderWorkflowService(
                orderRepository,
                cartRepository,
                productRepository,
                shippingService,
                shipmentRepository,
                couponRepository,
                userRepository,
                orderEventPublisher,
                shippingEventPublisher,
                warehouseRepository
        );
    }

    @Test
    void shouldReserveStockInWarehouseWhenOrderIsPlaced() {
        // given
        OrderItem item = new OrderItem("P123", 2, Money.of(BigDecimal.valueOf(50), "USD"));
        // when
        Order order = orderWorkflowService.placeOrder("user-1", List.of(item));
        // then
        assertEquals(8, warehouse.getAvailableQuantity("P123"));
        assertEquals(2, warehouse.getReservedQuantity("P123"));
        verify(orderRepository).save(order);
        verify(warehouseRepository).save(warehouse);
    }

    @Test
    void shouldFailToPlaceOrderWhenStockIsInsufficient() {
        // given
        OrderItem item = new OrderItem("P123", 15, Money.of(BigDecimal.valueOf(50),"USD"));

        // when / then
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> orderWorkflowService.placeOrder("user-1", List.of(item))
        );

        assertTrue(ex.getMessage().contains("Not enough stock"));
    }
}
