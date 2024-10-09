package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.Models.SoftwareCommand;
import com.InventoryCHU.Inventory.Repository.SoftwareCommandRepository;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SoftwareCommandService {
    @Autowired
    SoftwareCommandRepository softwareCommandRepository;

    public List<SoftwareCommand> getAllSoftwareCommands(){
        return softwareCommandRepository.findAll();
    }

    public SoftwareCommand insertSoftwareCommand(SoftwareCommand sc){
        Optional<SoftwareCommand> softwareCommand = softwareCommandRepository.getSoftwareCommandBySoftwareIdAndUserId(sc.getSoftwareId() , sc.getUserId());
        if(softwareCommand.isPresent())
            return null;
        return softwareCommandRepository.save(sc);
    }

    public Optional<SoftwareCommand> getSoftwareCommandById(String id){
        return softwareCommandRepository.findById(id);
    }

    public Optional<List<SoftwareCommand>> getSoftwareCommandByUserId(int userId){
        return softwareCommandRepository.getSoftwareCommandByUserId(userId);
    }

    public Optional<List<SoftwareCommand>> getSoftwareCommandBySoftwareId(String softwareId){
        return softwareCommandRepository.getSoftwareCommandBySoftwareId(softwareId);
    }

    public boolean confirmSoftwareCommand(String commandId , int id){
        Optional<SoftwareCommand> sc = softwareCommandRepository.findById(commandId);
        if(sc.isPresent()){
            SoftwareCommand softwareCommand = sc.get();
            softwareCommand.setConfirmed(true);
            softwareCommand.setConfirmationDate(LocalDateTime.now());
            softwareCommand.setValidateur(id);
            softwareCommandRepository.save(softwareCommand);
            return true;
        }

        return false;
    }


}
