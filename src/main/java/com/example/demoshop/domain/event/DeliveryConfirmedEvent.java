package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;


import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.ShipmentId;

public record DeliveryConfirmedEvent(ShipmentId shipmentId, String orderId) {

}
