package com.example.demo.DTO;

public class CaseDepartmentDTO {

    private String department;

    public CaseDepartmentDTO(String department) {
        this.department = department;
    }

    public String getDepartment(){
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
