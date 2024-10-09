package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.Models.ItemsType;
import com.InventoryCHU.Inventory.Services.ItemsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/types")
public class ItemsTypeController {

    @Autowired
    ItemsTypeService service;

    @GetMapping()
    public ResponseEntity<List<ItemsType>> getAllTypes(){
        return ResponseEntity.ok(service.getAllTypes());
    }
    @PostMapping()
    public ResponseEntity<Object> addType(@RequestBody ItemsType itemsType){
        if(itemsType.getItemType().isEmpty()){
            String repo = "Please Fill the Type";
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(repo);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addType(itemsType));
    }

    @PostMapping("/Many")
    public ResponseEntity<Void> addManyTypes(@RequestBody List<ItemsType> itemsTypes){
        service.addMany(itemsTypes);
        return ResponseEntity.noContent().build();
    }

}
