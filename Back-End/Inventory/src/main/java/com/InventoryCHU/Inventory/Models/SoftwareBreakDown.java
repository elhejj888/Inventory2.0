package com.InventoryCHU.Inventory.Models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "SoftwareBreakDown")
public class SoftwareBreakDown {
    @Id
    private String id;
    private String softwareId;
    private String software;
    private String service;
    private String description;
    private String report;
    private String remarque;
    private int userId;
    private int technicianId;
    private boolean isFixed = false;
    private boolean isDeclined = false;
    @CreatedDate
    private LocalDateTime declaredAt;
    private LocalDateTime fixedAt;
    private LocalDateTime declinedAt;

}
