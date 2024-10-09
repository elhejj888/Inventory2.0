package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.Models.ItemsType;
import com.InventoryCHU.Inventory.Repository.ItemsTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ItemsTypeService {
    @Autowired
    ItemsTypeRepository repository;

    public List<ItemsType> getAllTypes(){
        return repository.findAll();
    }

    public ItemsType addType(ItemsType itemsType){
        itemsType.setItemType(StringUtils.capitalize(itemsType.getItemType()));
        return repository.save(itemsType);
    }
    public List<ItemsType> addMany(List<ItemsType> types){
        for(ItemsType itemsType : types){
            itemsType.setItemType(StringUtils.capitalize(itemsType.getItemType()));
        }
        return repository.saveAll(types);
    }
}
