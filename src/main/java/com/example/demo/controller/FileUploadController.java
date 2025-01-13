package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.service.FileUploadService;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/upload")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/file")
    public ResponseEntity<Map<String, Object>> uploadFileDocuments(
                                 @RequestParam("fileId") Long fileId,
                                 @RequestParam("type") String type,
                                 @RequestParam("document") MultipartFile document
    ) throws IOException {
        Map<String, Object> response = new HashMap<>();
            Path documentDirectory = Paths.get("documents");
            Files.createDirectories(documentDirectory);

            String filePath = saveFile(document, documentDirectory, String.valueOf(fileId));

            fileUploadService.saveFile(fileId, type, filePath);

            response.put("success", true);
            response.put("message", "file successfully uploaded");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    private String saveFile(MultipartFile file, Path directory, String email) throws IOException {
        if (file != null && !file.isEmpty()) {
            String originalFileName = file.getOriginalFilename();
            String fileName = email + "-" + originalFileName;  // email-dosyaAdi.jpg
            Path filePath = directory.resolve(fileName);
            Files.write(filePath, file.getBytes());
            return filePath.toString();
        }
        return null;
    }
}

