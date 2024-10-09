package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.CommandConfirmation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CommandConfirmationRepository extends MongoRepository<CommandConfirmation , String > {
    Optional<CommandConfirmation> getCommandConfirmationByCommandId(String commandId);
}
