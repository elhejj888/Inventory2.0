package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.Models.Command;
import com.InventoryCHU.Inventory.Models.CommandDetail;
import com.InventoryCHU.Inventory.Models.Inventory;
import com.InventoryCHU.Inventory.Services.CommandService;
import org.hibernate.usertype.UserVersionType;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/Command")
public class CommandController {
    @Autowired
    CommandService service;

    @GetMapping
    public ResponseEntity<List<Command>> getAllCommands() {
        List<Command> Commands = service.getAllCommands();
        return ResponseEntity.ok(Commands);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Command> getCommandById(@PathVariable String id) {
        Optional<Command> command = service.getCommandById(id);
        return command.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/entry-{id}")
    public ResponseEntity<Command> findCommandByRecepientId(@PathVariable("id") Long recepientId){
        Optional<Command> command = service.findCommandByRecepientId(recepientId);
        return command.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createCommand(@RequestBody Command command) {
        Object createdCommand = service.addCommand(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCommand);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCommand(@PathVariable String id) {
        service.deleteCommand(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/test")
    public ResponseEntity<Object> getCurrentRole(){
        String msg = "";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserDetails){
            msg = "Current user: " + ((UserDetails) principal).getAuthorities();
        } else {
            msg = "Current user: " + principal.toString();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(msg);
    }
    @PutMapping("/cmd-{id}/{status}")
    public ResponseEntity<Object> updateStatus(@PathVariable("id") String id , @PathVariable("status") String status , @RequestBody String Uid){

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(service.updateStatus(id , status , Uid));
    }

    @PutMapping("/verify-{CommandId}/user-{techId}")
    public ResponseEntity<Object> verifyCommand(@RequestBody List<CommandDetail> cd ,
                                                @PathVariable("CommandId") String commandId ,
                                                @PathVariable("techId") int techId){
        Object resp = service.verifyCommand(techId,commandId,cd);
        if(resp instanceof Command){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(resp);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resp);
    }
}
