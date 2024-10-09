package com.InventoryCHU.Inventory.Services;
import com.InventoryCHU.Inventory.Models.UserInfo;
import com.InventoryCHU.Inventory.Repository.UserInfoRepository;
import org.python.antlr.ast.Str;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUser {

    private static UserInfoRepository repository;

    @Autowired
    public void setRepository(UserInfoRepository repository) {
        CurrentUser.repository = repository; // Inject into static field
    }

    public static Object getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null; // No user is authenticated
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            UserInfo userInfo = repository.findByUsername(userDetails.getUsername()).orElse(null);
            return userInfo; // Return userInfo of authenticated user
        } else {
            return principal.toString(); // In case of anonymous user or simple principal
        }
    }
}