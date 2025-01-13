package com.example.demo.DTO;

import java.time.LocalDate;


public class PatientUserFullDTO {
    private String email;
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthDate;
    private String gender;
    private String country;
    private String city;
    private Integer tickets;

    public PatientUserFullDTO(String email, String name, String surname, String phone, LocalDate birthDate,
                              String gender, String country, String city, Integer tickets) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.tickets = tickets;

    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public Integer getTickets() {
        return tickets;
    }


}
