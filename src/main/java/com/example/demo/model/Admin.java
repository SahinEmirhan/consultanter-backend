package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;


@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    private Boolean legalCapability;

    // Getter and Setter for legalCapability
    public Boolean getLegalCapability() {
        return legalCapability;
    }

    public Admin() {
    }

    public Admin(String hash, String salt, String email, String name, String surname, String phone,
                LocalDate birthDate, String gender, String country, String city, Boolean legalCapability) {
        super(hash, salt, email, name, surname, phone, birthDate, gender, country, city);
        this.legalCapability = legalCapability;
    }

    public static class Builder extends User.UserBuilder<Builder> {
        private Boolean legalCapability;

        public Builder setLegalCapability(
                @NotBlank(message = "legal capability must be written")
                Boolean legalCapability) {
            this.legalCapability = legalCapability;
            return this;
        }

        public Admin build() {
            return new Admin(this.hash, this.salt, this.email, this.name, this.surname, this.phone,
                    this.birthDate, this.gender, this.country, this.city, this.legalCapability);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
