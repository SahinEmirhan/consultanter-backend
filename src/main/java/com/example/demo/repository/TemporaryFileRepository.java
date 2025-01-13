package com.example.demo.repository;

import com.example.demo.model.TemporaryFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemporaryFileRepository extends JpaRepository<TemporaryFile, Long> {

    List<TemporaryFile> findByDepartmentIsNull();

    List<TemporaryFile> findByDepartment(String department);

    List<TemporaryFile> findByDepartmentIsNotNull();


}

