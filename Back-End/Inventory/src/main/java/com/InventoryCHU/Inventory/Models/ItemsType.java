package com.InventoryCHU.Inventory.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "ItemsType")
public class ItemsType {
    @Id
    private String id;
    private String itemType;
}
