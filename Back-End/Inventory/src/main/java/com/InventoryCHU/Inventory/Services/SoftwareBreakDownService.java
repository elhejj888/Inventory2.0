package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.FileManager.model.BreakDownReport;
import com.InventoryCHU.Inventory.Models.BreakDown;
import com.InventoryCHU.Inventory.Models.SoftwareBreakDown;
import com.InventoryCHU.Inventory.Models.SoftwareCommand;
import com.InventoryCHU.Inventory.Repository.SoftwareBreakDownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SoftwareBreakDownService {
    @Autowired
    SoftwareBreakDownRepository repository;

    public List<SoftwareBreakDown> getAllSoftwareBreakDowns(){
        return repository.findAll();
    }

    public Object saveSoftwareBreakDown(SoftwareBreakDown softwareBreakDown){
        Optional<SoftwareBreakDown> sbd = repository.findFirstBySoftwareIdAndUserId(softwareBreakDown.getSoftwareId() , softwareBreakDown.getUserId());
        if(!sbd.isPresent())
        return repository.save(softwareBreakDown);
        else return "Logiciel Déja en Panne..!";
    }

    public Optional<SoftwareBreakDown> getSoftwareBreakDownById(String id){
        return repository.findById(id);
    }

    public Optional<List<SoftwareBreakDown>> getSoftwareBreakdownByUserId(int id){
        return repository.findByUserId(id);
    }
    public Optional<List<SoftwareBreakDown>> getSoftwareBreakDownBySoftwareId(String softwareId){
        return repository.findBySoftwareId(softwareId);
    }

    public Object assignBreakdown(String id , int userId){
        Optional<SoftwareBreakDown> softwareBreakDown = repository.findById(id);
        if(softwareBreakDown.isPresent()){
            softwareBreakDown.get().setTechnicianId(userId);
            return repository.save(softwareBreakDown.get());
        }
        return "Not Found";

    }

    public Object breakDownFixed(String breakdown , BreakDownReport report){
        Optional<SoftwareBreakDown> optionalBreakDown = repository.findById(breakdown);
        if(optionalBreakDown.isPresent()){
            SoftwareBreakDown breakDown = optionalBreakDown.get();
            LocalDateTime dt = LocalDateTime.now();
            breakDown.setFixedAt(dt);
            breakDown.setFixed(true);
            breakDown.setReport(report.getReport());
            breakDown.setRemarque(report.getRemarque());
            return repository.save(breakDown);
        }
        return "Erreur : Panne non Trouvé..!";
    }

    public Object breakDownDeclined(String breakdown , BreakDownReport report){
        Optional<SoftwareBreakDown> bd = repository.findById(breakdown);
        if(bd.isPresent()){
            LocalDateTime dt = LocalDateTime.now();
            SoftwareBreakDown breakDown = bd.get();
            breakDown.setDeclined(true);
            breakDown.setDeclinedAt(dt);
            breakDown.setReport(report.getReport());
            breakDown.setRemarque( report.getRemarque());
            return repository.save(breakDown);
        }
        return "Erreur : Panne non Trouvé..!";

    }


}
