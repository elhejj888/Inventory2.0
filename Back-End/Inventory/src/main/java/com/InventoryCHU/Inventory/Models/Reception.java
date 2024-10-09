package com.InventoryCHU.Inventory.Models;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "Reception")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reception {
    @Id
    private String id;
    private String DeliveryId;
    private int quantity;
    private LocalDateTime dateReception;
    private Long ReceptionBy;
    private Long ReceptionTo;
}
