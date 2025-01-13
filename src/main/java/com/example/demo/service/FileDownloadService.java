package com.example.demo.service;

import com.example.demo.model.FileDocument;
import com.example.demo.repository.FileDocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileDownloadService {

    @Autowired
    private FileDocumentRepository fileDocumentRepository;



    @Transactional
    public List<FileDocument> getDicomFiles(Long id) {
        return fileDocumentRepository.findByFileIdAndType(id, "dicom");
    }

}
