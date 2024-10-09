package com.InventoryCHU.Inventory.Models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "softwareCommand")
public class SoftwareCommand {
    @Id
    private String id;
    private String softwareId;
    private boolean isConfirmed=false;
    @CreatedDate
    private LocalDateTime commandDate;
    private LocalDateTime confirmationDate;
    private int validateur;
    private int userId;
    private String userService;
}
