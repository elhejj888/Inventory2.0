package com.InventoryCHU.Inventory.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "Delivery")
public class Delivery {
    @Id
    private String id;
    private String commandId;
    private boolean isFullyConfirmed = false; //if the items on the command are all received the infos will be empty
    private List<CommandDetail> itemsList;
    @CreatedDate
    private LocalDateTime deliveryDate;
    private Long DeliveredBy;
}
