package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.SoftwareCommand;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SoftwareCommandRepository extends MongoRepository<SoftwareCommand,String> {
    Optional<List<SoftwareCommand>> getSoftwareCommandByUserId(int userId);
    Optional<List<SoftwareCommand>> getSoftwareCommandBySoftwareId(String softwareId);

    Optional<SoftwareCommand> getSoftwareCommandBySoftwareIdAndUserId(String softwareId , int userId);
}
