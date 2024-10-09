package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.StockEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockEntryRepository extends MongoRepository<StockEntry, String> {
}