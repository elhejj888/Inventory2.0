package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.Models.Command;
import com.InventoryCHU.Inventory.Models.Delivery;
import com.InventoryCHU.Inventory.Repository.DeliveryRepository;
import org.python.antlr.op.Del;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    CommandService commandService;

    public List<Delivery> findAllDeliveries(){
        return deliveryRepository.findAll();
    }

    public Object declareDelivery(Delivery delivery){
        Optional<Command> command = commandService.getCommandById(delivery.getCommandId());
        if(command.isPresent()){
            command.get().setDelivered(true);
            commandService.addCommand(command.get());
            return deliveryRepository.save(delivery);
        }
        else return "Something Wrong ... no Command found with the Given Id..!";
    }

}
