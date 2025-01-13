package com.example.demo.model;

import com.example.demo.service.PasswordUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hash;
    private String salt;

    @Column(name = "email", unique = true, nullable = false)
    private String email;
    private String name;
    private String surname;
    private String phone;
    private LocalDate birthDate;
    private String gender;
    private String country;
    private String city;

    @Column(insertable=false, updatable=false, name = "user_type")
    private String userType;

    public User() {
    }

    public User(String hash, String salt, String email, String name, String surname, String phone,
                LocalDate birthDate, String gender, String country, String city) {
        this.hash = hash;
        this.salt = salt;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.country = country;
        this.city = city;

    }

    public abstract static class UserBuilder <T extends UserBuilder<T>> {

        protected String hash;
        protected String salt;
        protected String email;
        protected String name;
        protected String surname;
        protected String phone;
        protected LocalDate birthDate;
        protected String gender;
        protected String country;
        protected String city;



        public T setEmail(String email) {
            this.email = email;
            return self();
        }

        public T setName(String name) {
            this.name = name;
            return self();
        }

        public T setSurname(String surname) {
            this.surname = surname;
            return self();
        }

        public T setPhone(String phone) {
            this.phone = phone;
            return self();
        }

        public T setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return self();
        }

        public T setGender(String gender) {
            this.gender = gender;
            return self();
        }

        public T setCountry(String country) {
            this.country = country;
            return self();
        }

        public T setCity(String city) {
            this.city = city;
            return self();
        }

        public T setPassword(String password) {
            try {
                String[] hashedData = PasswordUtil.hashPassword(password);
                this.salt = hashedData[0];
                this.hash = hashedData[1];
                return self();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error occurred while hashing the password", e);
            }

        }

        protected abstract T self();


    }


}
