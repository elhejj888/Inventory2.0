package com.InventoryCHU.Inventory.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "Item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @Id
    private String id;
    private String reference;
    private String nInventaire;
    private String itemType;
    private String etat;
    private String service;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private int userId;

    public Item(String nRef , String nInventaire , String etat , String itemType){
        this.reference = nRef;
        this.nInventaire = nInventaire;
        this.etat = etat;
        this.itemType = itemType;
    }

}
