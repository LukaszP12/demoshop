package example.demoshop.application.shipping;

import example.demoshop.domain.model.order.Order;
import example.demoshop.domain.model.shipping.Shipment;
import example.demoshop.domain.model.shipping.TrackingNumber;
import example.demoshop.domain.model.shipping.Address;
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
