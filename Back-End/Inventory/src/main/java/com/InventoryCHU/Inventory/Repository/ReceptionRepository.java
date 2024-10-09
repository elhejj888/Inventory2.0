package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.Reception;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceptionRepository extends MongoRepository<Reception, String> {
    Optional<Reception> findReceptionByDeliveryId(String id);
}