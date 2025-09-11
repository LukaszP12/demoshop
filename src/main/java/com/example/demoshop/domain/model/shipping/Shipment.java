package main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Address;
import java.time.Instant;

public class Shipment {
    private final ShipmentId id;
    private final String orderId;
    private final Address shippingAddress;
    private TrackingNumber trackingNumber;
    private boolean delivered;
    private final Instant createdAt;

    public Shipment(String orderId, Address shippingAddress) {
        this.id = ShipmentId.newId();
        this.orderId = orderId;
        this.shippingAddress = shippingAddress;
        this.createdAt = Instant.now();
        this.delivered = false;
    }

    public void assignTrackingNumber(TrackingNumber trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void markDelivered() {
        this.delivered = true;
    }

    public boolean isDelivered() { return delivered; }
    public ShipmentId id() { return id; }
    public String orderId() { return orderId; }
    public Address shippingAddress() { return shippingAddress; }
    public TrackingNumber trackingNumber() { return trackingNumber; }
    public Instant createdAt() { return createdAt; }
}
