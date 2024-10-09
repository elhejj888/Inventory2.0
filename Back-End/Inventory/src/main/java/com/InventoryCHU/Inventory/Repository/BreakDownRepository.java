package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.BreakDown;
import org.python.antlr.ast.Str;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface BreakDownRepository extends MongoRepository<BreakDown , String> {
    Optional<BreakDown> findBreakDownByItemId(String item);
    Optional<List<BreakDown>> findBreakDownByTechnicianId(int id);
    Optional<List<BreakDown>> findByTechnicianIdIsNull();
    int countBreakDownByItemId(String itemId);
    int countBreakDownByIsFixed(boolean fixed);
    int countAllByIsDeclined(boolean declined);

    Optional<List<BreakDown>> findAllByDeclaredBy(int id);
}
