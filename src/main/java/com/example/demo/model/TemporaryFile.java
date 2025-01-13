package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data
public class TemporaryFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @Column(nullable = false)
    private String complaint;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Double height;

    @Column(nullable = false)
    private Double weight;

    @Column(nullable = false, length = 1000)
    private String knownConditions;

    private String department;

    public TemporaryFile() {
    }

    public TemporaryFile(User patient, String complaint,
                         LocalDate date, Double height,
                         Double weight, String knownConditions) {
        this.patient = patient;
        this.complaint = complaint;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.knownConditions = knownConditions;
        this.department = null;
    }


    private TemporaryFile(Builder builder) {
        this.patient = builder.patient;
        this.complaint = builder.complaint;
        this.date = builder.date;
        this.height = builder.height;
        this.weight = builder.weight;
        this.knownConditions = builder.knownConditions;
        this.department = builder.department;
    }

    public static class Builder {
        private User patient;
        private String complaint;
        private LocalDate date;
        private Double height;
        private Double weight;
        private String knownConditions;
        private String department;

        public Builder setPatient(User patient) {
            this.patient = patient;
            return this;
        }

        public Builder setComplaint(String complaint) {
            this.complaint = complaint;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = (date != null) ? date : LocalDate.now();
            return this;
        }

        public Builder setHeight(Double height) {
            this.height = height;
            return this;
        }

        public Builder setWeight(Double weight) {
            this.weight = weight;
            return this;
        }

        public Builder setKnownConditions(String knownConditions) {
            this.knownConditions = knownConditions;
            return this;
        }

        public Builder setDepartment() {
            this.department = null;
            return this;
        }

        public TemporaryFile build() {
            return new TemporaryFile(this);
        }
    }


}
