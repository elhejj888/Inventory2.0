package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.Models.Item;
import com.InventoryCHU.Inventory.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Item")
public class ItemController {
    @Autowired
    ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        Optional<Item> item = itemService.getItemById(id);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user-{id}")
    public ResponseEntity<Item> getItemByUserId(@PathVariable("id") Long userId){
        Optional<Item> item = itemService.getItemByUserId(userId);
        return item.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/ref-{type}")
    public Optional<List<Item>> getItemsByNRef(String ref){
        return itemService.getItemsByNRef(ref);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item createdItem = itemService.createItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PostMapping("/saveMany")
    ResponseEntity<List<Item>> saveManyItems(@RequestBody List<Item> items){

        List<Item> itemList = itemService.saveManyItems(items);
        return ResponseEntity.ok(itemList);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteMany")
    public ResponseEntity<Void> deleteMany(@RequestBody List<String> ids){
        itemService.deleteMany(ids);
        return ResponseEntity.noContent().build();
    }

}

