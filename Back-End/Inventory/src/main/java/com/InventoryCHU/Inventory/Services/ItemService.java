package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.Models.Item;
import com.InventoryCHU.Inventory.Models.UserInfo;
import com.InventoryCHU.Inventory.Repository.ItemRepository;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public List<Item> getAllItems(){
        Object user = CurrentUser.getCurrentUser();
        System.out.println(user);
        if(user instanceof UserInfo) {
            UserInfo userInfo = (UserInfo) user;
            if (userInfo.getRoles().equals("Responsable Hardware") ||
                    userInfo.getRoles().equals("Technicien Materiel") ||
                    userInfo.getRoles().equals("Responsable de Stock"))
                return itemRepository.findAll();

            else return itemRepository.getItemsByUserId(userInfo.getId());
        }

        return null;

    }

    public Optional<Item> getItemById(String Id){
        return itemRepository.findById(Id);
    }

    public Item createItem(Item item){
        return itemRepository.save(item);
    }
    public List<Item> saveManyItems(List<Item> items){
        return itemRepository.saveAll(items);
    }

    public void deleteItem(String Id){
        itemRepository.deleteById(Id);
    }

    public Optional<Item> getItemByUserId(Long userId){ return itemRepository.getItemByUserId(userId); }

    public Optional<List<Item>> getItemsByNRef(String ref){
        return itemRepository.findItemsByReference(ref);
    }

    public Object fixItem(String id){
        Optional<Item> item = itemRepository.findById(id);
        if(item.isPresent()){
            item.get().setEtat("Broke");
            return itemRepository.save(item.get());
        }
        else return "No item found with Given ID..!";
    }

    public List<Item> getAllByUserIdEmptyAndItemType( String itemType , int userId){
        return itemRepository.findItemsByItemTypeAndUserId( itemType , userId);
    }

    public List<Item> findByUserIdAndItemType(String type){
        return itemRepository.findByItemType(type);
    }

    public void deleteMany(List<String> ids){
        itemRepository.deleteAllById(ids);
    }
}
