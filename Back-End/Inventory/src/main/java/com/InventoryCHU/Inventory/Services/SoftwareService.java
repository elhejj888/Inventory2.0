package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.Models.Software;
import com.InventoryCHU.Inventory.Models.SoftwareCommand;
import com.InventoryCHU.Inventory.Repository.SoftwareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SoftwareService {
    @Autowired
    SoftwareRepository softwareRepository;

    @Autowired
    SoftwareCommandService softwareCommandService;

    public List<Software> findAllSoftwares(){
        return softwareRepository.findAll();
    }

    public Optional<Software> findSoftwareById(String id){
        return softwareRepository.findById(id);
    }

    public Software AddSoftware(Software software){
        return softwareRepository.save(software);
    }

    public List<Software> AddManySoftwares(List<Software> softwares){
        return softwareRepository.saveAll(softwares);
    }

    public String deleteSoftware(String id){
        softwareRepository.deleteById(id);
        return "Deleted Successfully";
    }

    public Object fixSoftware(String id){
        Optional<Software> software = softwareRepository.findById(id);
        if(software.isPresent()){
            software.get().setPannes(software.get().getPannes()+1);
            softwareRepository.save(software.get());
            return software.get();
        }
        return "Software not Found";
    }

    public Software updateSoftware(String id, Software software) {
        Software software1 = softwareRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Software not found with id: " + id));

        if (software.getImg() != null) software1.setImg(software.getImg());
        if (software.getDetail() != null) software1.setDetail(software.getDetail());
        if (software.getDescription() != null) software1.setDescription(software.getDescription());
        if (software.getLicense() != null) software1.setLicense(software.getLicense());
        if (software.getTitle() != null) software1.setTitle(software.getTitle());
        return softwareRepository.save(software1);
    }

    public List<Software> getSoftwaresByUserId(int id){
        List<SoftwareCommand> softwareCommands = softwareCommandService.getSoftwareCommandByUserId(id).get();
        List<Software> softwares = new ArrayList<>();
        if(!softwareCommands.isEmpty()) {
            for (SoftwareCommand sc : softwareCommands) {
                softwares.add(softwareRepository.findById(sc.getSoftwareId()).get());
            }
        }
        return softwares;
    }
}
