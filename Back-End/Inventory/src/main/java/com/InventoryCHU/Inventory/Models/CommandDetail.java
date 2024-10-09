package com.InventoryCHU.Inventory.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class CommandDetail {
    public String itemType;
    public int quantity;
}
