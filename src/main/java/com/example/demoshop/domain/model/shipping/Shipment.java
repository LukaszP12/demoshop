package com.example.demoshop.domain.model.shipping;

import com.example.demoshop.domain.model.user.Address;
import java.time.Instant;

public class Shipment {
    private final main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.ShipmentId id;
    private final String orderId;
    private final Address shippingAddress;
    private main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.TrackingNumber trackingNumber;
    private boolean delivered;
    private final Instant createdAt;

    public Shipment(String orderId, Address shippingAddress) {
        this.id = main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.ShipmentId.newId();
        this.orderId = orderId;
        this.shippingAddress = shippingAddress;
        this.createdAt = Instant.now();
        this.delivered = false;
    }

    public void assignTrackingNumber(main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.TrackingNumber trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void markDelivered() {
        this.delivered = true;
    }

    public boolean isDelivered() { return delivered; }
    public main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.ShipmentId id() { return id; }
    public String orderId() { return orderId; }
    public Address shippingAddress() { return shippingAddress; }
    public main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.TrackingNumber trackingNumber() { return trackingNumber; }
    public Instant createdAt() { return createdAt; }
}
