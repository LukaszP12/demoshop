package com.example.demoshop.application.shipping;

import com.example.demoshop.domain.model.order.Order;
import com.example.demoshop.domain.model.shipping.Shipment;
import com.example.demoshop.domain.model.user.Address;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.TrackingNumber;
import org.springframework.stereotype.Service;

@Service
public class ShippingService implements ShippingPort {

    public Shipment createShipment(String orderId, Address address) {
        Shipment shipment = new Shipment(orderId, address);
        shipment.assignTrackingNumber(new TrackingNumber("TRACK-" + orderId));
        return shipment;
    }

    @Override
    public void shipOrder(Order order) {
        // Here you would integrate with a real shipping API (e.g., UPS, FedEx)
        // For demo, we can just print/log
        System.out.println("Shipping order " + order.getId() + " for user " + order.getUserId());
    }
}
