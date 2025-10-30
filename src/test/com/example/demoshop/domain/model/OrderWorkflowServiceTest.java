package com.example.demoshop.domain.model;

import example.demoshop.application.order.OrderWorkflowService;
import example.demoshop.application.shipping.ShippingEventPublisher;
import example.demoshop.application.shipping.ShippingRoutingKey;
import example.demoshop.application.shipping.ShippingService;
import example.demoshop.domain.event.PaymentSuccessfulEvent;
import example.demoshop.domain.model.common.Money;
import example.demoshop.domain.model.shipping.Address;
import example.demoshop.domain.model.order.Order;
import example.demoshop.domain.model.shipping.Shipment;
import example.demoshop.domain.repository.OrderRepository;
import example.demoshop.domain.repository.ShipmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderWorkflowServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ShippingService shippingService;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private ShippingEventPublisher shippingEventPublisher;

    @InjectMocks
    private OrderWorkflowService orderWorkflowService;

    @Test
    void shouldMarkOrderPaidAndShipOnPaymentSuccess() {
        String orderId = "order-123";
        PaymentSuccessfulEvent event = new PaymentSuccessfulEvent(orderId, Money.zero("USD"));
        Address shippingAddress = new Address("Street 1", "City", "12345", "Country");

        Order order = Mockito.mock(Order.class);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Shipment shipment = Mockito.mock(Shipment.class);
        when(shippingService.createShipment(orderId, shippingAddress)).thenReturn(shipment);

        orderWorkflowService.handlePaymentSuccess(event, shippingAddress);

        InOrder inOrder = inOrder(orderRepository, order, shippingService, shipmentRepository, shippingEventPublisher);

        inOrder.verify(orderRepository).findById(orderId);
        inOrder.verify(order).markPaid();
        inOrder.verify(orderRepository).save(order);
        inOrder.verify(shippingService).createShipment(orderId, shippingAddress);
        inOrder.verify(shipmentRepository).save(shipment);
        inOrder.verify(shippingEventPublisher).publish(any(),eq(ShippingRoutingKey.SHIPMENT_CREATED));
        inOrder.verify(order).markShipped();
        inOrder.verify(orderRepository).save(order);
    }
}
