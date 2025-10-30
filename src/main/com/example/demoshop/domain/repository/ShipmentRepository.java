package example.demoshop.domain.repository;

import example.demoshop.domain.model.shipping.Shipment;
import example.demoshop.domain.model.shipping.ShipmentId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ShipmentRepository extends MongoRepository<Shipment, ShipmentId> {
    Shipment save(Shipment shipment);
    Optional<Shipment> findById(ShipmentId id);
}
