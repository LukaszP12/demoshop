package main.java.com.example.demoshop.java.com.example.demoshop.domain.event;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.ShipmentId;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.TrackingNumber;

public record OrderShippedEvent(ShipmentId shipmentId, String orderId, TrackingNumber trackingNumber) {

}
