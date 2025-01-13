package com.example.demo.service;

import com.example.demo.model.FileDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.repository.FileDocumentRepository;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    @Autowired
    private final FileDocumentRepository fileDocumentRepository;

    public void saveFile(Long fileId, String type, String filePath) {
        FileDocument fileDocument = FileDocument.builder()
                .fileId(fileId)
                .type(type)
                .fileData(filePath)
                .build();

        fileDocumentRepository.save(fileDocument);
    }
}

