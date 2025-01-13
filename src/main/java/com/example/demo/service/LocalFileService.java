package com.example.demo.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalFileService {

    public Resource loadFileAsResource(String fullPath) throws IOException {
        try {
            Path filePath = Paths.get(fullPath).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new IOException("File not found: " + fullPath);
            }
        } catch (MalformedURLException e) {
            throw new IOException("File path is invalid: " + fullPath, e);
        }
    }
}
