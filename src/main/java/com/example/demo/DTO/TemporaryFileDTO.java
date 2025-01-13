package com.example.demo.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class TemporaryFileDTO {

    private Long id;
    private Long patientId;

    @NotBlank(message="complaint must be written")
    @Size(max=5000, message = "Complaint must be 0-5000 characters long")
    private String complaint;

    private LocalDate date;

    @NotNull(message="height must be written")
    private Double height;

    @NotNull(message="weight must be written")
    private Double weight;

    @Size(max=300, message = "known conditions must be 0-300 characters long")
    private String knownConditions;

    public TemporaryFileDTO() {}

    // Constructor
    public TemporaryFileDTO(Long id, Long patientId, String complaint,
                            LocalDate date, Double height,
                            Double weight, String knownConditions) {
        this.id = id;
        this.patientId = patientId;
        this.complaint = complaint;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.knownConditions = knownConditions;
    }
    public TemporaryFileDTO(String complaint,
                            LocalDate date, Double height,
                            Double weight, String knownConditions) {
        this.complaint = complaint;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.knownConditions = knownConditions;
    }


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

    @Override
    public String toString() {
        return "TemporaryFileDTO{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", complaint='" + complaint + '\'' +
                ", date=" + date +
                ", height=" + height +
                ", weight=" + weight +
                ", knownConditions='" + knownConditions + '\'' +
                '}';
    }
}
