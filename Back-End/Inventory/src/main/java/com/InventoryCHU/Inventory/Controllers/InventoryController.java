package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.Models.Inventory;
import com.InventoryCHU.Inventory.Models.Item;
import com.InventoryCHU.Inventory.Models.RequestItem;
import com.InventoryCHU.Inventory.Services.InventoryService;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/Inventory")
public class InventoryController {

    @Autowired
    InventoryService service;

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventories() {
        List<Inventory> inventories = service.getAllInventories();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable String id) {
        Optional<Inventory> inventory = service.getInventoryById(id);
        return inventory.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        Inventory createdInventory = service.createInventory(inventory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdInventory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable String id) {

        if(service.deleteInventory(id)){
            return ResponseEntity.status(HttpStatus.OK).body("Supprimee avec succes..!");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suppression Impossible..!");
    }

    @GetMapping("/Item-{type}")
    public Optional<List<Inventory>> getInventoriesByItemType(@PathVariable("type") String type){
        return service.getInventoriesByItemType(type);
    }

    @PostMapping("/requestItem")
    public ResponseEntity<Object> createInventoryWithItems(@RequestBody RequestItem items){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createInventoryWithItems(items));
    }
}
