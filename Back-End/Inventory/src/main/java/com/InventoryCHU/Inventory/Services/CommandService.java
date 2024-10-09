package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.Models.*;
import com.InventoryCHU.Inventory.Repository.CommandRepository;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommandService {
    @Autowired
    CommandRepository repository;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    ItemService itemService;

    public List<Command> getAllCommands(){
        Object user = CurrentUser.getCurrentUser();
        System.out.println(user);
        if(user instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) user;
            if (userInfo.getRoles().equals("Responsable Hardware") ||
                    userInfo.getRoles().equals("Technicien Materiel") ||
                    userInfo.getRoles().equals("Responsable de Stock"))
                return repository.findAll();
            else
                return repository.findAllByRecepient(userInfo.getId()).get();
        }

        else return null;
    }

    public Optional<Command> getCommandById(String Id){
        return repository.findById(Id);
    }

    public Object createCommand(Command command){
        for (CommandDetail commandDetail : command.getInfos()){
            Optional<Inventory> inventory = inventoryService.getInventorieByItemType(StringUtils.capitalize(commandDetail.itemType));
            if(inventory != null ){
                Inventory inv = inventory.get();
            if(commandDetail.quantity <= inv.getQuantity()){
                inv.setQuantity(inventory.get().getQuantity() - commandDetail.quantity);
                inventoryService.updateInventory(inv);
                return repository.save(command);
            }

            else {
                return "error Adding the Command , Inventory is only : " + inv.getQuantity();

            }

            }

        }
        return "Inventory Empty , Command Impossible ..!";

    }

    public Object addCommand(Command command){
        List<CommandDetail> cd = new ArrayList<>();
        for(CommandDetail commandDetail : command.getInfos()){
            commandDetail.setItemType(StringUtils.capitalize(commandDetail.getItemType()));
            cd.add(commandDetail);
        }
        command.setInfos(cd);
        return repository.save(command);
    }

    public void deleteCommand(String Id){
        repository.deleteById(Id);
    }

    public Optional<Command> findCommandByRecepientId(Long RecepientId){ return repository.findCommandByRecepient(RecepientId); }

    public Object confirmCommand(Command command){
        command.setConfirmed(true);
        return repository.save(command);
    }
    public Object deliverCommand(Command command){
        command.setDelivered(true);
        return repository.save(command);
    }

    public Object updateStatus(String id , String status , String resp){
        Optional<Command> cmd  = repository.findById(id);
        if(cmd.isPresent()){
            List<String> signatures = cmd.get().getSignatures();
            Command command = cmd.get();
            command.setStatus(status);
            if (status.equals("Validee") )
                command.setConfirmed(true);
            else if(status.equals("Confirmee")) {
                signatures.add(resp);
                command.setSignatures(signatures);
            }
            else if(status.equals("Livree")) {
                int isDelivered = 0;
                command.setDelivered(true);
                signatures.add(resp);
                command.setSignatures(signatures);
                for(CommandDetail cd : command.getDeliveredItems()){
                    List<Item> items = itemService.
                            getAllByUserIdEmptyAndItemType(cd.itemType, 0);
                    List<Item> items1 = itemService.findByUserIdAndItemType(cd.itemType);
                    System.out.println(items.size()+" : "+cd.quantity+"--"+cd.itemType);
                    System.out.println("**"+items1.size()+" : "+cd.quantity+"--"+cd.itemType);
                    List<Item> finalItems = new ArrayList<>();
                    for(Item item:items1){
                        if(item.getUserId() == 0){
                            finalItems.add(item);
                        }
                    }
                    if(finalItems.size()>=cd.quantity){
                        for (int i = 0; i < cd.quantity; i++) {
                            finalItems.get(i).setUserId(command.getRecepient());
                        }
                        itemService.saveManyItems(finalItems);
                        isDelivered++;
                    }
                }
                System.out.println(isDelivered + " --- "+command.getDeliveredItems().size());
                if(isDelivered!=command.getDeliveredItems().size()){
                    return "Stock Epuise..!";
                }
                command.setReceptionDate(LocalDateTime.now());
            }
            repository.save(command);
            return "Updated Successfully ..!";
        }
        return "Command doesn't exist ..!";
    }

    public Object verifyCommand(int techId , String commandId , List<CommandDetail> delivered){
        Optional<Command> commandOptional = repository.findById(commandId);
        if(commandOptional.isPresent()){
            Command command = commandOptional.get();
            command.setVerifiedBy(techId);
            command.setDeliveredItems(delivered);
            command.setStatus("Verifiee");
            return repository.save(command);
        }
        return "Command not Found..!";
    }




}
