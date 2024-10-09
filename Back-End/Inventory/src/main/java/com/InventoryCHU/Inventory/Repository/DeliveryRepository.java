package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryRepository extends MongoRepository<Delivery, String> {
    Optional<Delivery> findDeliveriesByCommandId(String id);
}