package main.java.com.example.demoshop.java.com.example.demoshop.application.shipping;

import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.Shipment;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.TrackingNumber;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.user.Address;

public class ShippingService {
    public Shipment createShipment(String orderId, Address address) {
        Shipment shipment = new Shipment(orderId, address);
        shipment.assignTrackingNumber(new TrackingNumber("TRACK-" + orderId));
        return shipment;
    }
}
