package example.demoshop.domain.event;

import example.demoshop.domain.model.shipping.ShipmentId;

public record DeliveryConfirmedEvent(ShipmentId shipmentId, String orderId) {

}
