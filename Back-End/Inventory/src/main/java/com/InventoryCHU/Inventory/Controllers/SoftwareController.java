package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.Models.Software;
import com.InventoryCHU.Inventory.Models.SoftwareBreakDown;
import com.InventoryCHU.Inventory.Services.SoftwareService;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Software")
public class SoftwareController {
    @Autowired
    SoftwareService service;

    @GetMapping
    public ResponseEntity<List<Software>> getAllSoftwares(){
        List<Software> softwares = service.findAllSoftwares();
        return ResponseEntity.ok(softwares);

    }

    @GetMapping("/Software-{id}")
    public ResponseEntity<Software> getSoftwarebyId(@PathVariable("id") String id){
        Optional<Software> software = service.findSoftwareById(id);
        return software.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> addSoftware(@RequestBody Software software){
        Object createdSoftware = service.AddSoftware(software);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSoftware);

    }

    @PostMapping("/AddManySoftwares")
    public ResponseEntity<List<Software>> addManySoftwares(@RequestBody List<Software> softwares){
        List<Software> softwaresList = service.AddManySoftwares(softwares);
        return ResponseEntity.status(HttpStatus.CREATED).body(softwaresList);
    }

    @DeleteMapping("/del-{id}")
    public Object DeleteSoftware(@PathVariable("id") String id){
        return service.deleteSoftware(id);
    }


    @PutMapping("/update-{id}")
    public ResponseEntity<Software> updateBreakdown(@PathVariable("id") String id , @RequestBody Software software){
        Software updatedSoftware = service.updateSoftware(id, software);
        return ResponseEntity.ok(updatedSoftware); // Return 200 OK with updated software
    }

    @GetMapping("/user-{id}")
    public ResponseEntity<List<Software>> getSoftwaresByUserId(@PathVariable("id") int id){
        return ResponseEntity.ok(service.getSoftwaresByUserId(id));
    }
}
