package com.InventoryCHU.Inventory.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.python.antlr.ast.Str;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Inventory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors
public class Inventory {
    @Id
    private String id;
    private String itemType;
    private String reference;
    private String marque;
    private String description;
    private String entryId;
    private int quantity;
    private int minQuantity = 10;
    private String etat;
    private int insertedBy;
    @CreatedDate
    public LocalDateTime insertedAt;
    @LastModifiedDate
    public LocalDateTime modifiedAt;

    public Inventory(String itemType , String nRef , String marque ,
                     String description , String entryId , int quantity ,
                     int minQuantity , int insertedBy , String etat){
        this.itemType = itemType;
        this.reference = nRef;
        this.marque = marque;
        this.description = description;
        this.entryId = entryId;
        this.quantity = quantity;
        this.minQuantity = minQuantity;
        this.insertedBy = insertedBy;
        this.etat = etat;
    }

}
