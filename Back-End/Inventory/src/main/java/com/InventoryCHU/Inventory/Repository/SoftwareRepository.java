package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.Software;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SoftwareRepository extends MongoRepository<Software,String> {

}
