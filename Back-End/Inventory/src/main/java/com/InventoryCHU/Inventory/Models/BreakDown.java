package com.InventoryCHU.Inventory.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import org.python.antlr.ast.Str;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "BreakDown")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreakDown {
    @Id
    public String id;
    public String itemId;
    public String Service;
    public String report;
    public String panneType;
    public String description;
    public String remarque;
    public boolean isDeclined;
    public boolean isFixed;
    public int declaredBy;
    public int technicianId;
    @CreatedDate
    public LocalDateTime breakedAt;
    public LocalDateTime fixedAt;

}
