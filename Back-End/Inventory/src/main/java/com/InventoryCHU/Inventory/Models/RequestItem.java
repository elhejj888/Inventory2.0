package com.InventoryCHU.Inventory.Models;

import lombok.Data;

import java.util.List;

@Data
public class RequestItem {
    private String itemType;
    private String nRef;
    private String marque;
    private String description;
    private String entryId;
    private String etat;
    private List<String> inventoryIds;
    private int insrtedBy;
    private int quantity;
    private int minQuantity;
}
