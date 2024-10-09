package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.Models.Inventory;
import com.InventoryCHU.Inventory.Models.Item;
import com.InventoryCHU.Inventory.Models.RequestItem;
import com.InventoryCHU.Inventory.Repository.InventoryRepository;
import com.InventoryCHU.Inventory.Repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService  {
    @Autowired
    InventoryRepository repository;

    @Autowired
    ItemRepository itemRepository;
    public List<Inventory> getAllInventories(){
        return repository.findAll();
    }

    public Optional<Inventory> getInventoryById(String Id){
        return repository.findById(Id);
    }

    public Inventory createInventory(Inventory inventory){
        inventory.setItemType(StringUtils.capitalize(inventory.getItemType()));
        Optional<Inventory> inv = repository.getInventorieByItemType(inventory.getItemType());
        if(!inv.isPresent())
        return repository.save(inventory);
        else {
            inv.get().setQuantity(inv.get().getQuantity() + inventory.getQuantity());
            return repository.save((inv.get()));
        }

    }

    public Object createInventoryWithItems(RequestItem items){
        Inventory inventory = new Inventory(items.getItemType(),items.getNRef(),
                items.getMarque(),items.getDescription(), items.getEntryId(),
                items.getQuantity(), items.getMinQuantity() , items.getInsrtedBy(),
                items.getEtat());
        Optional<Inventory> ifExisted = repository.findFirstByReference(inventory.getReference());
        if(ifExisted.isPresent()){
            Inventory inv = ifExisted.get();
            inv.setQuantity(inv.getQuantity() + inventory.getQuantity());
            repository.save(inv);
        }
        else {
            Inventory saved = repository.save(inventory);
        }
        for (String inventoryId : items.getInventoryIds()){
            Item item = new Item(items.getNRef(), inventoryId ,items.getEtat() , items.getItemType());
            itemRepository.save(item);
        }

        return "successfully saved";
    }

    public boolean deleteInventory(String Id){
        Inventory inventory = repository.findById(Id).get();
        Optional<List<Item>> optionalItems = itemRepository.findItemsByReference(inventory.getReference());
        if(optionalItems.isPresent()) {
            List<Item> items = optionalItems.get();
            int cpt = 0;
            for (Item item : items) {
                cpt++;
                if (item.getUserId() != 0) {
                    return false;
                }
            }
            if(cpt==items.size()){
                itemRepository.deleteAll(items);
            }

        }
        repository.deleteById(Id);
        return true;

    }
    public Optional<Inventory> findInventoryByReference(String ref){
        return repository.findFirstByReference(ref);
    }



    public Optional<Inventory> getInventorieByItemType(String itemType){
        return repository.getInventorieByItemType(itemType);
    }

    public Optional<List<Inventory>> getInventoriesByItemType(String itemType){
        return repository.getInventoriesByItemType(itemType);
    }

    public Inventory updateInventory(Inventory inv){
        return repository.save(inv);
    }

    public boolean checkItemsAssignement(List<Item> items){
        for(Item item : items){
            if(item.getUserId() != 0){
                return false;
            }
        }
        return true;
    }

}

