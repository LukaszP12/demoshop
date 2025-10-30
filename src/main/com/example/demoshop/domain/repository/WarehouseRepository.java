package example.demoshop.domain.repository;

import example.demoshop.domain.model.warehouse.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WarehouseRepository extends MongoRepository<Warehouse,String> {
}
