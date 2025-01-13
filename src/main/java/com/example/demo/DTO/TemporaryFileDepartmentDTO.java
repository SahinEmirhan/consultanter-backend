package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TemporaryFileDepartmentDTO {

    @NotNull(message = "id must be written")
    private Long id;

    @NotBlank(message = "department must be setted")
    private String department;

    // Getter ve Setter metodları
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}