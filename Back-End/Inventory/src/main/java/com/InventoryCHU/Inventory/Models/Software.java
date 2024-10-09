package com.InventoryCHU.Inventory.Models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Software")
@Data

public class Software {
    @Id
    private String id;
    private String title;
    private String detail;
    private String description;
    private String img;
    private String license;
    private String Service;
    private int pannes;
}
