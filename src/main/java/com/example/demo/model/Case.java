package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"case\"")
public class Case {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    @ToString.Exclude
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="doctor_id")
    @JsonIgnore
    @ToString.Exclude
    private User doctor;

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

    @Column(length = 300)
    private String summaryDiagnosis;

    @Column(length = 2000)
    private String detailedDiagnosis;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    @JsonIgnore
    @ToString.Exclude
    private Comment comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_id")
    @JsonIgnore
    @ToString.Exclude
    private Rate rate;

    private Date meetingDate;

    private boolean closure;

    private boolean requiresSecondOpinion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="consulter_id")
    @JsonIgnore
    @ToString.Exclude
    private User consultingDoctor;

    private String consultText;

    public Case(Long id, User patient, User doctor, String complaint,
                         LocalDate date, Double height,
                         Double weight, String knownConditions, String department) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.complaint = complaint;
        this.date = date;
        this.height = height;
        this.weight = weight;
        this.knownConditions = knownConditions;
        this.department = department;
        this.summaryDiagnosis = null;
        this.detailedDiagnosis = null;
    }

    private Case(Builder builder) {
        this.id = builder.id;
        this.patient = builder.patient;
        this.doctor = builder.doctor;
        this.complaint = builder.complaint;
        this.date = builder.date;
        this.height = builder.height;
        this.weight = builder.weight;
        this.knownConditions = builder.knownConditions;
        this.department = builder.department;
        this.summaryDiagnosis = builder.summaryDiagnosis;
        this.detailedDiagnosis = builder.detailedDiagnosis;
        this.requiresSecondOpinion = builder.requiresSecondOpinion;
    }

    public static class Builder {
        private Long id;
        private User patient;
        private User doctor;
        private String complaint;
        private LocalDate date;
        private Double height;
        private Double weight;
        private String knownConditions;
        private String department;
        private String summaryDiagnosis;
        private String detailedDiagnosis;
        private boolean requiresSecondOpinion;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setPatient(
                @NotBlank(message = "patient must be setted")
                User patient) {
            this.patient = patient;
            return this;
        }

        public Builder setDoctor(
                @NotBlank(message = "doctor must be setted")
                User doctor) {
            this.doctor = doctor;
            return this;
        }

        public Builder setComplaint(String complaint) {
            this.complaint = complaint;
            return this;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
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

        public Builder setDepartment(String department) {
            this.department = department;
            return this;
        }

        public Builder setSummaryDiagnosis() {
            this.summaryDiagnosis = null;
            return this;
        }

        public Builder setDetailedDiagnosis() {
            this.detailedDiagnosis = null;
            return this;
        }

        public Builder setRequiresSecondOpinion(boolean opinion) {
            this.requiresSecondOpinion = opinion;
            return this;
        }

        public Case build() {
            return new Case(this);
        }
    }

}
