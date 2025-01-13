package com.example.demo.repository;

import com.example.demo.DTO.DepartmentDTO;
import com.example.demo.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @Query("SELECT new com.example.demo.DTO.DepartmentDTO(d.name) FROM Department d")
    List<DepartmentDTO> findAllDepartmentsAsDTO();
}
