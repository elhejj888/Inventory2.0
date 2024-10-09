package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.Services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/stats")
public class StatsController {
    @Autowired
    StatsService service;

    @GetMapping()
    public ResponseEntity<Object> getAllStats(){
        return ResponseEntity.ok(service.getAllStats());
    }


}
