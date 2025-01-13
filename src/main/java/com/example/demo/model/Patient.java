package com.example.demo.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Data
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue("PATIENT")
public class Patient extends User {

    private Integer tickets;

    @OneToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    @ToString.Exclude
    private User user;

    public Patient(){

    }

    public Patient(String hash, String salt, String email, String name, String surname, String phone,
                   LocalDate birthDate, String gender, String country, String city, Integer tickets){
        super(hash, salt, email, name, surname, phone, birthDate, gender, country, city);
        this.tickets = tickets;
    }

    public static class Builder extends User.UserBuilder<Builder> {

        private Integer tickets;

        public Builder setTickets() {
            this.tickets = 0;
            return this;
        }

        public Patient build() {
            return new Patient(this.hash, this.salt, this.email, this.name, this.surname, this.phone,
                    this.birthDate, this.gender, this.country, this.city, this.tickets);
        }

        @Override
        protected Builder self(){
            return this;
        }
    }
}
