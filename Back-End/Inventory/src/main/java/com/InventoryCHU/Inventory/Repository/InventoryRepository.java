package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.Inventory;
import org.python.antlr.ast.Str;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Optional<Inventory> getInventorieByItemType(String itemType);
    Optional<List<Inventory>> getInventoriesByItemType(String itemType);

    Optional<Inventory> findFirstByReference(String reference);
}