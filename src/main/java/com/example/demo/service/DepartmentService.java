package com.example.demo.service;

import com.example.demo.DTO.DepartmentDTO;
import com.example.demo.model.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(String name) {
        Department department = new Department.DepartmentBuilder()
                .setName(name)
                .build();
        return departmentRepository.save(department);
    }

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAllDepartmentsAsDTO();

    }
}
