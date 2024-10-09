package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.FileManager.model.BreakDownReport;
import com.InventoryCHU.Inventory.Models.BreakDown;
import com.InventoryCHU.Inventory.Services.BreakDownService;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/breakdown")
public class BreakDownController {
    @Autowired
    BreakDownService breakDownService;

    @GetMapping
    public ResponseEntity<List<BreakDown>> getAllBreakDowns() {
        List<BreakDown> breakDowns = breakDownService.getAllBreakDowns();
        return ResponseEntity.ok(breakDowns);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreakDown> getBreakDownById(@PathVariable String id) {
        Optional<BreakDown> breakDown = breakDownService.getBreakDownById(id);
        return breakDown.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/product-{id}")
    public ResponseEntity<BreakDown> getBreakDownByUserId(@PathVariable("id") String productId){
        Optional<BreakDown> breakDown = breakDownService.findBreakDownByItemId(productId);
        return breakDown.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createBreakDown(@RequestBody BreakDown breakDown) {
        Object createdBreakDown = breakDownService.createBreakDown(breakDown);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBreakDown);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBreakDown(@PathVariable String id) {
        breakDownService.deleteBreakDown(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/assign-{PanneId}/tech-{techID}")
    public ResponseEntity<Object> assignBreakDown(@PathVariable("PanneId") String panneId,
                                                  @PathVariable("techID") int techID) {
        // Call the service method and return its result
        Object result = breakDownService.assignBreakDown(panneId, techID);

        // Check the type of result and return appropriate ResponseEntity
        if (result instanceof BreakDown) {
            return ResponseEntity.ok(result);  // Return 200 OK with the updated BreakDown object
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);  // Return 404 if there's an error
        }
    }

    @PutMapping("/fix-{panneId}")
    public ResponseEntity<Object> breakDownFixed(@PathVariable("panneId") String breakdown ,
                                                 @RequestBody BreakDownReport report){
        Object result = breakDownService.breakDownFixed(breakdown , report);
        // Check the type of result and return appropriate ResponseEntity
        if (result instanceof BreakDown) {
            return ResponseEntity.ok(result);  // Return 200 OK with the updated BreakDown object
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);  // Return 404 if there's an error
        }
    }

    @PutMapping("/decline-{id}")
    public ResponseEntity<Object> declineBreakDown(@PathVariable("id") String id ,
                                                   @RequestBody BreakDownReport report){
        Object breakDown = breakDownService.breakDownDeclined(id , report);
        if(breakDown instanceof BreakDown){
            return ResponseEntity.ok(breakDown);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(breakDown);
    }

}
