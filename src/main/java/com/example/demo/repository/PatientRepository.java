package com.example.demo.repository;

import com.example.demo.DTO.PatientUserFullDTO;
import com.example.demo.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Patient findByEmail(String email);

    @Query("SELECT new com.example.demo.DTO.PatientUserFullDTO(u.email, u.name, u.surname, u.phone, " +
            "u.birthDate, u.gender, u.country, u.city, d.tickets) " +
            "FROM Patient d JOIN d.user u " +
            "WHERE d.id = :id")
    PatientUserFullDTO findPatientFullDetailsById(@Param("id") Long id);
}
