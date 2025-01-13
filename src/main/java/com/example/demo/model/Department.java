package com.example.demo.model;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class DepartmentBuilder {
        private String name;

        public DepartmentBuilder() {
            this.name = null;
        }

        public DepartmentBuilder setName(
                @NotBlank(message = "department must be written")
                String name) {
            this.name = name.toLowerCase();
            return this;
        }

        public Department build() {
            return new Department(name);
        }
    }
}
