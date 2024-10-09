package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.Item;
import com.InventoryCHU.Inventory.Models.ItemsType;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemsTypeRepository extends MongoRepository<ItemsType , String> {
}
