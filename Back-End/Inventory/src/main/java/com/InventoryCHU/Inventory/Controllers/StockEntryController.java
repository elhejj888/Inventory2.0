package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.Models.Item;
import com.InventoryCHU.Inventory.Models.StockEntry;
import com.InventoryCHU.Inventory.Services.StockEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/entry")
public class StockEntryController {

    @Autowired
    StockEntryService service;

    @GetMapping
    public ResponseEntity<List<StockEntry>> getAllEntries() {
        List<StockEntry> entries = service.getAllEntries();
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockEntry> getEntryById(@PathVariable String id) {
        Optional<StockEntry> entry = service.getEntryById(id);
        return entry.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<StockEntry> createStockEntry(@RequestBody StockEntry entry) {
        StockEntry createdEntry = service.createStockEntry(entry);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntry);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStockEntry(@PathVariable String id) {
        service.deleteStockEntry(id);
        return ResponseEntity.noContent().build();
    }

}
