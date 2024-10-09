package com.InventoryCHU.Inventory.Controllers;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.Files.write;

@RestController
@RequestMapping("/api/images")
public class imgController {

    private final String uploadDir = "uploads/";

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
        }
        // Process the file (e.g., save it)
        String imageName = file.getOriginalFilename();
        Path imgPath = Paths.get(uploadDir,imageName);
        Files.createDirectories(imgPath.getParent());
        Files.write(imgPath,file.getBytes());
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/view/{imageName}")
    public ResponseEntity<Resource> viewImage(@PathVariable String imageName) throws IOException {
        Path imagePath = Paths.get(uploadDir, imageName);

        if (Files.exists(imagePath)) {
            Resource imageResource = new UrlResource(imagePath.toUri());
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Adjust this if serving PNG, GIF, etc.
                    .body(imageResource);
        }

        return ResponseEntity.notFound().build();
    }
}

