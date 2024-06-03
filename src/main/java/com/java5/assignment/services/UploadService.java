package com.java5.assignment.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class UploadService {
    public String uploadFile(MultipartFile file) {
        if (!file.isEmpty()) {
            Path root = Paths.get("uploads");
            try {
                Files.createDirectories(root);
                String name = String.valueOf(new Date().getTime());
                String fileName = String.format("%s%s", name, ".jpg");
                Files.copy(file.getInputStream(), ((java.nio.file.Path) root).resolve(fileName));
                return fileName;
            } catch (IOException e) {
                System.out.println(e.getMessage());
                throw new RuntimeException("Could not initialize folder for upload!");
            }
        }
        return null;
    }

    public Boolean remove(String name){
        try {
            Path root = Paths.get(String.format("static/images/%s", name));
            return Files.deleteIfExists(root);
        }
        catch (Exception e){
            return false;
        }
    }
}

