package com.example.demo.DTO;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DoctorUserFullDTO {


    private String specialization;
    private boolean adminApproval;
    private int doctorType;


    private String email;
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthDate;
    private String gender;
    private String country;
    private String city;

    private String profilePhoto;

    private String taxPlate;

    private String certificatePhoto;

    private String degreePhoto;


    public DoctorUserFullDTO(String email, String name, String surname, String phone,
                             LocalDate birthDate, String gender, String country, String city,
                             String specialization, Boolean adminApproval,
                             String profilePhoto, String degreePhoto, String certificatePhoto,
                             String taxPlate, int doctorType) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.taxPlate = taxPlate;
        this.specialization = specialization;
        this.adminApproval = adminApproval;
        this.profilePhoto = profilePhoto;
        this.degreePhoto = degreePhoto;
        this.certificatePhoto = certificatePhoto;
        this.doctorType = doctorType;
    }

}
