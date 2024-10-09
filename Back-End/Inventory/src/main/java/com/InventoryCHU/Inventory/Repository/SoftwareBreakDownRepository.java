package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.SoftwareBreakDown;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SoftwareBreakDownRepository extends MongoRepository<SoftwareBreakDown, String> {
    Optional<List<SoftwareBreakDown>> findByTechnicianId(int id);
    Optional<List<SoftwareBreakDown>> findBySoftwareId(String id);
    Optional<List<SoftwareBreakDown>> findByUserId(int id);
    Optional<SoftwareBreakDown> findFirstBySoftwareIdAndUserId(String softwareId , int userId);
}
