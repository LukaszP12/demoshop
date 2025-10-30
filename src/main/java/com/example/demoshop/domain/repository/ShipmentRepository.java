package com.example.demoshop.domain.repository;

import com.example.demoshop.domain.model.shipping.Shipment;
import main.java.com.example.demoshop.java.com.example.demoshop.domain.model.shipping.ShipmentId;

import java.util.Optional;

public interface ShipmentRepository {
    Shipment save(Shipment shipment);
    Optional<Shipment> findById(ShipmentId id);
}
