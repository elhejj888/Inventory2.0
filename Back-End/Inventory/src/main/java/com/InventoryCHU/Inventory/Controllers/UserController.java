package com.InventoryCHU.Inventory.Controllers;

import com.InventoryCHU.Inventory.Models.AuthRequest;
import com.InventoryCHU.Inventory.Models.UserInfo;
import com.InventoryCHU.Inventory.Services.JwtService;
import com.InventoryCHU.Inventory.Services.UserInfoService;
import org.python.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome ...";
    }

    private final String uploadDir = "AdminsUploads/";

    @PostMapping("/addNewUser")
    public ResponseEntity<Map<String, Object>> addNewUser(
            @RequestParam("email") String email,
            @RequestParam("username") String username,
            @RequestParam("role") String role,
            @RequestParam("software") String software,
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("service") String service2
    ) {
        Map<String, Object> response = new HashMap<>();

        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setUsername(username);
        userInfo.setRoles(role);
        userInfo.setSoftware(software);
        userInfo.setPassword(password);
        userInfo.setFirstName(firstName);
        userInfo.setLastName(lastName);
        userInfo.setService(service2);

        // Check if the username or email already exists
        Object usr = service.addUser(userInfo);
        if (usr instanceof String) {
            response.put("success", false);
            response.put("message", usr);  // This will be your error message (e.g., "Username exists")
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // If user is successfully registered
        response.put("success", true);
        response.put("message", "User registered successfully");
        response.put("user", usr);

        return ResponseEntity.ok(response);
    }



    @GetMapping("/admin/profile")
    @PreAuthorize("hasAnyAuthority('Admin')")
    public String adminProfile(){
        return "welcome Admin";
    }

    @GetMapping("/recruiter/profile")
    @PreAuthorize("hasAnyAuthority('Majeur')")
    public String MajeurProfile(){
        return "welcome Majeur";
    }

    @GetMapping("/candidate/profile")
    @PreAuthorize("hasAnyAuthority('Technicien')")
    public String TechnicienProfile(){
        return "welcome Technicien";
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest){
        authRequest.setUsername(authRequest.getUsername().toLowerCase());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername() , authRequest.getPassword()
                ));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUsername());
        }
        else {
            throw new UsernameNotFoundException("Invalid Username");
        }
    }

    @PostMapping("/generateToken2")
    public ResponseEntity<Map<String, Object>> authenticateAndGetToken2(@RequestBody AuthRequest authRequest) {
        authRequest.setUsername(authRequest.getUsername().toLowerCase());

        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(authRequest.getUsername());

                // Get user details (replace with your logic to retrieve user information)
                UserDetails userDetails = service.loadUserByUsername(authRequest.getUsername());
                Optional<UserInfo> usr = service.getUser(authRequest.getUsername());

                Map<String, Object> response = new HashMap<>();
                response.put("user", usr); // Replace with your user data structure
                response.put("token", token);

                return ResponseEntity.ok(response);
            } else {
                // If the authentication isn't authenticated, return 401 Unauthorized
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
            }
        } catch (UsernameNotFoundException ex) {
            // Return a 404 if the username was not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Username not found"));
        } catch (BadCredentialsException ex) {
            // Return a 401 if the password is incorrect
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Incorrect password"));
        } catch (Exception ex) {
            // Catch other exceptions and return a 500 Internal Server Error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "An internal error occurred"));
        }
    }




    @PostMapping("subscribe")
    public ResponseEntity<Map<String, Object>> addNewUser2(@RequestBody UserInfo userInfo){
        Object info = service.addUser(userInfo);
        if(info instanceof UserInfo){
            String token = jwtService.generateToken(((UserInfo) info).getUsername());
            Map<String, Object> response = new HashMap<>();
            response.put("user", info); // Replace with your user data structure
            response.put("token", token);
            return ResponseEntity.ok(response);
        }
        else {
            throw new UsernameNotFoundException("Invalid Credentials");
        }
    }

    @GetMapping("/role")
    public ResponseEntity<Object> findByRoles(){
        List<UserInfo> users = service.findByRole();
        return ResponseEntity.ok(users);

    }

    @GetMapping("/softwareTechs")
    public ResponseEntity<Object> findSoftwareTechs(){
        List<UserInfo> users = service.findSoftwareTechs();
        return ResponseEntity.ok(users);

    }
}
