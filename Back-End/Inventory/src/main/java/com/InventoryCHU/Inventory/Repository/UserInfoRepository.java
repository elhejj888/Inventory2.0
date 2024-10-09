package com.InventoryCHU.Inventory.Repository;

import com.InventoryCHU.Inventory.Models.UserInfo;
import org.python.antlr.ast.Str;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo , Integer> {
    Optional<UserInfo> findByUsername(String username);
    Optional<List<UserInfo>> findUserInfosByRoles(String role);

    int countAllByRoles(String role);

    Optional<UserInfo> findFirstByEmail(String email);

    @Query("select distinct u.roles from UserInfo u")
    List<String> findDistinctRoles();

}
