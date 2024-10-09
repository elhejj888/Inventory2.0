package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.Models.CommandConfirmation;
import com.InventoryCHU.Inventory.Repository.CommandConfirmationRepository;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommandConfirmationService {
    @Autowired
    CommandConfirmationRepository repository;

    public Object addSignature(String CommandId , String userId){
        Optional<CommandConfirmation> confirmation = repository.getCommandConfirmationByCommandId(CommandId);
        if(confirmation.isPresent()){
            CommandConfirmation ccf = confirmation.get();
            ccf.addSignature(userId);
            repository.save(ccf);
            return "signed Successfully";
        }
        return "Command doesn't exist";

    }

    public Object addCommandConfirmation(String commandId){
        CommandConfirmation confirmation = new CommandConfirmation();
        confirmation.setCommandId(commandId);
        return repository.save(confirmation);

    }
    public Object getConfirmationDetails(String commandId){
        Optional<CommandConfirmation> confirmation = repository.getCommandConfirmationByCommandId(commandId);
        if(confirmation.isPresent()){
            CommandConfirmation commandConfirmation = confirmation.get();
            return commandConfirmation;
        }
        return "Command doesn't exist";
    }

}
