package com.InventoryCHU.Inventory.Models;

import lombok.Data;
import org.python.antlr.ast.Str;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "CommandConfirmation")
public class CommandConfirmation {
    @Id
    private String Id;
    private String commandId;
    private List<String> sigantures;

    public void addSignature(String userID){
        this.sigantures.add(userID);
    }


}
