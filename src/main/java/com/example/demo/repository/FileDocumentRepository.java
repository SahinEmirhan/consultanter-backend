package com.example.demo.repository;

import com.example.demo.model.FileDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileDocumentRepository extends JpaRepository<FileDocument, Long> {
    List<FileDocument> findByFileIdAndType(Long id, String document);

}