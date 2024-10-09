package com.InventoryCHU.Inventory.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Document(collection = "Command")
@Data
public class Command {
    @Id
    private String id;
    private int recepient;
    private List<String> signatures = new ArrayList<>();
    private boolean isConfirmed = false;
    private boolean isDelivered = false;
    private String status = "Brouillon";
    private List<CommandDetail> infos;
    private List<CommandDetail> deliveredItems;
    private int verifiedBy;
    @CreatedDate
    private LocalDateTime commandDate;
    private LocalDateTime receptionDate;


}
