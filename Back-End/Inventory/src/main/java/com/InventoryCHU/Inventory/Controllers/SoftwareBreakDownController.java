package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.FileManager.model.BreakDownReport;
import com.InventoryCHU.Inventory.Models.BreakDown;
import com.InventoryCHU.Inventory.Models.SoftwareBreakDown;
import com.InventoryCHU.Inventory.Models.SoftwareCommand;
import com.InventoryCHU.Inventory.Services.SoftwareBreakDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/softwareBreakDown")
public class SoftwareBreakDownController {
    @Autowired
    SoftwareBreakDownService service;

    @GetMapping
    public ResponseEntity<List<SoftwareBreakDown>> getAllSoftwareBreakDowns(){
        return ResponseEntity.ok(service.getAllSoftwareBreakDowns());
    }

    @PostMapping
    public ResponseEntity<Object> saveSoftwareBreakDown(@RequestBody SoftwareBreakDown softwareBreakDown){
        Object bd  = service.saveSoftwareBreakDown(softwareBreakDown);

        if(bd instanceof SoftwareBreakDown)
            return ResponseEntity.status(HttpStatus.CREATED).body(bd);

        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(bd);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoftwareBreakDown> getSoftwareBreakDownById(@PathVariable("id") String id){
        Optional<SoftwareBreakDown> softwareBreakDown = service.getSoftwareBreakDownById(id);
        if(softwareBreakDown.isPresent()) {
            return ResponseEntity.status(HttpStatus.FOUND).body(softwareBreakDown.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new SoftwareBreakDown());
    }
    @PutMapping("/assign-{id}/tech-{userId}")
    public ResponseEntity<Object> assignBreakdown(@PathVariable("id") String id, @PathVariable("userId") int userId){
    Object res = service.assignBreakdown(id,userId);
    if(res instanceof SoftwareBreakDown){
        return ResponseEntity.ok(res);
    }
    else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(res);
    }

    @PutMapping("/fix-{panneId}")
    public ResponseEntity<Object> breakDownFixed(@PathVariable("panneId") String breakdown ,
                                                 @RequestBody BreakDownReport report){
        Object result = service.breakDownFixed(breakdown , report);
        // Check the type of result and return appropriate ResponseEntity
        if (result instanceof SoftwareBreakDown) {
            return ResponseEntity.ok(result);  // Return 200 OK with the updated BreakDown object
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);  // Return 404 if there's an error
        }
    }

    @PutMapping("/decline-{id}")
    public ResponseEntity<Object> declineBreakDown(@PathVariable("id") String id ,
                                                   @RequestBody BreakDownReport report){
        Object breakDown = service.breakDownDeclined(id , report);
        if(breakDown instanceof SoftwareBreakDown){
            return ResponseEntity.ok(breakDown);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(breakDown);
    }


}

