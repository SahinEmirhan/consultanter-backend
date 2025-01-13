package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@DiscriminatorValue("DOCTOR")
public class Doctor extends User {

    private String specialization;
    private boolean adminApproval;
    private int doctorType;


    @OneToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    private String profilePhoto;

    private String taxPlate;

    private String certificatePhoto;

    private String degreePhoto;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<Rate> rates = new ArrayList<>();

    private Long reviewedCase;

    public Doctor() {
    }

    public Doctor(String hash, String salt, String email, String name, String surname, String phone,
                  LocalDate birthDate, String gender, String country, String city,
                  String specialization, Boolean adminApproval,
                  String profilePhoto, String degreePhoto, String certificatePhoto,
                  String taxPlate) {
        super(hash, salt, email, name, surname, phone, birthDate, gender, country, city);
        this.specialization = specialization;
        this.adminApproval = adminApproval;
        this.profilePhoto = profilePhoto;
        this.degreePhoto = degreePhoto;
        this.certificatePhoto = certificatePhoto;
        this.taxPlate = taxPlate;
        this.reviewedCase = 0L;
    }


    public static class Builder extends User.UserBuilder<Builder>{

        private String specialization;
        private boolean adminApproval;
        private int doctorType;
        private String profilePhoto;
        private String degreePhoto;
        private String certificatePhoto;
        private String taxPlate;

        public Builder setAdminApproval() {
            this.adminApproval = false;
            return this;
        }


        public Builder setProfilePhoto(String profilePhoto) {
            this.profilePhoto = profilePhoto;
            return this;
        }

        public Builder setDegreePhoto(String degreePhoto) {
            this.degreePhoto = degreePhoto;
            return this;
        }

        public Builder setCertificatePhoto(String certificatePhoto) {
            this.certificatePhoto = certificatePhoto;
            return this;
        }

        public Builder setTaxPlate(String taxPlate) {
            this.taxPlate = taxPlate;
            return this;
        }

        public Doctor build() {
            return new Doctor(
                    this.hash, this.salt, this.email, this.name, this.surname, this.phone,
                    this.birthDate, this.gender, this.country, this.city,
                    this.specialization, this.adminApproval,
                    this.profilePhoto, this.degreePhoto, this.certificatePhoto,
                    this.taxPlate
            );
        }

        @Override
        protected Doctor.Builder self() {
            return this;
        }
    }

    public String getSpecialization(){
        return specialization;
    }
    public Boolean getAdminApproval(){
        return adminApproval;
    }
    public Integer getDoctorType(){
        return doctorType;
    }
    public String getProfilePhoto(){
        return profilePhoto;
    }
    public String getDegreePhoto(){
        return degreePhoto;
    }
    public String getCertificatePhoto(){
        return certificatePhoto;
    }
    public String getTaxPlate(){
        return taxPlate;
    }
    public User getUser() {return user;}

    public List<Comment> getComments() {
        return this.comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Rate> getRates(){
        return this.rates;
    }

    public void setRates(List<Rate> rates){
        this.rates = rates;
    }

    public void addRate(Rate rate){
        this.rates.add(rate);
    }

    public void addComment(Comment comment){
        this.comments.add(comment);
    }

    public void setAdminApproval(){
        this.adminApproval = true;
    }

    public void setSpecialization(String specialization){
        this.specialization = specialization;
    }

    public void setDoctorType(int doctorType) { this.doctorType = doctorType;}

    public void incrementReviewedCase() {
        this.reviewedCase += 1;
    }
}
