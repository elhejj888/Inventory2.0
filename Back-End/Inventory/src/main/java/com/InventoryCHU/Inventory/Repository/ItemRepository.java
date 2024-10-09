package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.Item;
import org.python.antlr.ast.Str;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
    Optional<Item> getItemByUserId(Long userId);
    List<Item> getItemsByUserId(int userId);

    Optional<List<Item>> findItemsByReference(String nRef);


    List<Item> findItemsByItemTypeAndUserId(String itemType , int userId);

    List<Item> findByItemType( String itemType);

    int countAllByItemType(String itemType);
    int countAllByUserId(int userId);
}