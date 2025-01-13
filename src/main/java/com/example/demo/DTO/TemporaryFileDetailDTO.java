package com.example.demo.DTO;

import java.time.LocalDate;
import java.util.List;

public class TemporaryFileDetailDTO {
    private Long id;
    private Long patientId;
    private String complaint;
    private LocalDate date;
    private Double height;
    private Double weight;
    private String knownConditions;
    private String department;
    private List<FileDocumentDTO> documents;

    // Getter ve Setter metodları
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getKnownConditions() {
        return knownConditions;
    }

    public void setKnownConditions(String knownConditions) {
        this.knownConditions = knownConditions;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<FileDocumentDTO> getDocuments() {
        return documents;
    }

    public void setDocuments(List<FileDocumentDTO> documents) {
        this.documents = documents;
    }
}
