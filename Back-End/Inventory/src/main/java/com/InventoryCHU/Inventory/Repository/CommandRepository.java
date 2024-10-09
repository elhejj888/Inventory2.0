package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.Command;
import org.python.antlr.ast.Str;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommandRepository extends MongoRepository<Command, String> {
    Optional<Command> findCommandByRecepient(Long recepient);
    Optional<List<Command>> findAllByRecepient(int recepient);

    int countAllByStatus(String status);

}