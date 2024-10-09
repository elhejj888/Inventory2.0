package com.InventoryCHU.Inventory.Services;

import com.InventoryCHU.Inventory.Models.UserInfo;
import com.InventoryCHU.Inventory.Repository.UserInfoRepository;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {
    @Autowired
    UserInfoRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        username = username.toLowerCase();
        Optional<UserInfo> userDetail = repository.findByUsername(username);
        String finalUsername = username;
        return userDetail.map(UserinfoDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("the User "+ finalUsername +" Doesn't exist"));
    }

    public Object addUser(UserInfo userInfo){
        Optional<UserInfo> userInfoOptional = repository.findByUsername(userInfo.getUsername());
        if(userInfoOptional.isPresent()){
            return "Inscription Impossible ..!Nom d'utilisateur exist déja.";
        }
        userInfoOptional = repository.findFirstByEmail(userInfo.getEmail());
        if(userInfoOptional.isPresent()){
            return "Inscription Impossible..!Adresse Electronique existe déja.";
        }


        userInfo.setUsername(userInfo.getUsername().toLowerCase());
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        return repository.save(userInfo);
    }

    public Optional<UserInfo> getUser(String username){
        username=username.toLowerCase();
        return repository.findByUsername(username);
    }

    public Optional<UserInfo> findUserById(int id){
        return repository.findById(id);
    }

    public List<UserInfo> findByRole(){
        List<UserInfo> usersOptional = repository.findUserInfosByRoles("Technicien Materiel").get();
        return usersOptional;
    }

    public List<UserInfo> findSoftwareTechs(){
        List<UserInfo> usersOptional = repository.findUserInfosByRoles("Technicien Logiciel").get();
        return usersOptional;
    }

}
