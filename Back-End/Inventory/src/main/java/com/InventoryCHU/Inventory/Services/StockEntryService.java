package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.Models.Item;
import com.InventoryCHU.Inventory.Models.StockEntry;
import com.InventoryCHU.Inventory.Repository.StockEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockEntryService {
    @Autowired
    StockEntryRepository stockEntryRepository;

    public List<StockEntry> getAllEntries(){
        return stockEntryRepository.findAll();
    }

    public Optional<StockEntry> getEntryById(String Id){
        return stockEntryRepository.findById(Id);
    }

    public StockEntry createStockEntry(StockEntry entry){
        return stockEntryRepository.save(entry);
    }

    public void deleteStockEntry(String Id){
        stockEntryRepository.deleteById(Id);
    }

}
