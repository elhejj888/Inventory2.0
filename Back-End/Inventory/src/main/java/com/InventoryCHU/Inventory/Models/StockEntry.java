package com.InventoryCHU.Inventory.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "StockEntry")
@Data
public class StockEntry {
    @Id
    private String id;
    private String fournisseur;
    private String nFacture;
    @CreatedDate
    private LocalDateTime insertedAt;
    private LocalDate entryDate;


}
