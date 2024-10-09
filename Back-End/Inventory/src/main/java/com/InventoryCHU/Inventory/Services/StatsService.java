package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.FileManager.model.BreakDownReport;
import com.InventoryCHU.Inventory.Models.BreakDown;
import com.InventoryCHU.Inventory.Models.ItemsType;
import com.InventoryCHU.Inventory.Repository.*;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {
    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    BreakDownRepository breakDownRepository;

    @Autowired
    CommandRepository commandRepository;

    @Autowired
    ItemsTypeRepository itemsTypeRepository;

    public Object getAllStats(){
        int nTechMaterial = userInfoRepository.countAllByRoles("Technicien Materiel");
        int nTechLogiciel = userInfoRepository.countAllByRoles("Technicien Logiciel");
        int nRespLogiciel = userInfoRepository.countAllByRoles("Responsable Software");
        int nRespMateriel = userInfoRepository.countAllByRoles("Responsable Hardware");
        int nRespStock = userInfoRepository.countAllByRoles("Responsable de Stock");
        int nChefService = userInfoRepository.countAllByRoles("Chef de Service");

        int nCommandeConfirmed = commandRepository.countAllByStatus("Confirmee");
        int nCommandeDelivered = commandRepository.countAllByStatus("Livree");
        int nCommandeVerified = commandRepository.countAllByStatus("Verifiee");
        int nCommandeBrouillon = commandRepository.countAllByStatus("Brouillon");

        List<ItemsType> itemTypes = itemsTypeRepository.findAll();
        Map<String , Integer> itemsQuantity = new HashMap<>();
        for(ItemsType type : itemTypes){
            itemsQuantity.put(type.getItemType(),itemRepository.countAllByItemType(type.getItemType()));
        }

        int nBreakdowns = breakDownRepository.findAll().size();
        int nBreakdownsByFixed = breakDownRepository.countBreakDownByIsFixed(true);
        int nBreakdownsByDeclined = breakDownRepository.countAllByIsDeclined(true);



        // Create a map to hold the response data
        Map<String, Object> response = new HashMap<>();
        response.put("techniciansMaterial", nTechMaterial);
        response.put("techniciansSoftware", nTechLogiciel);
        response.put("responsibleSoftware", nRespLogiciel);
        response.put("responsibleHardware", nRespMateriel);
        response.put("responsibleStock", nRespStock);
        response.put("serviceChief", nChefService);
        response.put("confirmedOrders", nCommandeConfirmed);
        response.put("deliveredOrders", nCommandeDelivered);
        response.put("verifiedOrders", nCommandeVerified);
        response.put("draftOrders", nCommandeBrouillon);
        response.put("itemsQuantity", itemsQuantity);
        response.put("totalBreakdowns", nBreakdowns);
        response.put("breakdownsFixed", nBreakdownsByFixed);
        response.put("breakdownsDeclined", nBreakdownsByDeclined);

        return response;





    }
}
