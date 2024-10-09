package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.Models.SoftwareCommand;
import com.InventoryCHU.Inventory.Services.SoftwareCommandService;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/softwareCommand")
public class SoftwareCommandController {
    @Autowired
    SoftwareCommandService service;

    @GetMapping
    public ResponseEntity<List<SoftwareCommand>> getAllSoftwareCommands(){
        return ResponseEntity.ok(service.getAllSoftwareCommands());
    }

    @PostMapping
    public ResponseEntity<Object> saveSoftwareCommand(@RequestBody SoftwareCommand softwareCommand){
        SoftwareCommand sc = service.insertSoftwareCommand(softwareCommand);
        if (sc != null)
            return ResponseEntity.status(HttpStatus.CREATED).body(sc);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Logiciel Deja Demande..!");
    }

    @GetMapping("/userSoftwares")
    public ResponseEntity<List<SoftwareCommand>> getUserSoftwareCommands(@RequestBody int id){
        Optional<List<SoftwareCommand>> softwareCommands = service.getSoftwareCommandByUserId(id);
        if(softwareCommands.isPresent()){
            return ResponseEntity.ok(softwareCommands.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.EMPTY_LIST);
    }

    @GetMapping("/Softwares")
    public ResponseEntity<List<SoftwareCommand>> getSoftwareCommandsBySoftwareId(@RequestBody String id){
        Optional<List<SoftwareCommand>> softwareCommands = service.getSoftwareCommandBySoftwareId(id);
        if(softwareCommands.isPresent()){
            return ResponseEntity.ok(softwareCommands.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.EMPTY_LIST);
    }

    @PutMapping("/cmd-{id}/user-{userId}")
    public ResponseEntity<Object> confirmSoftwareCommand(@PathVariable("id") String commandId , @PathVariable("userId") int id)
    {
        boolean result = service.confirmSoftwareCommand(commandId,id);
        if (result){
            return ResponseEntity.ok("Command Successfully Updated");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Command Not Found");
    }
}
