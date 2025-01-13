package com.example.demo.repository;

import com.example.demo.DTO.CaseProfileDTO;
import com.example.demo.DTO.WebSocketDepartmentCaseDTO;
import com.example.demo.model.Case;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CaseRepository extends JpaRepository<Case, Long> {

    @Query("SELECT d.doctor.id FROM Case d WHERE d.id = :caseId")
    Long findDoctorIdByCaseId(@Param("caseId") Long caseId);

    @Query("SELECT new com.example.demo.DTO.CaseProfileDTO(d.id, d.complaint, d.date) FROM Case d WHERE d.patient = :patientId and d.closure = false")
    List<CaseProfileDTO> findMineCasesAsDTOAsPatient(@Param("patientId") User patient);


    @Query("SELECT new com.example.demo.DTO.CaseProfileDTO(d.id, d.complaint, d.date) FROM Case d WHERE d.doctor = :doctorId and d.closure = false")
    List<CaseProfileDTO> findMineCasesAsDTOAsDoctor(@Param("doctorId") User doctor);

    @Query("SELECT new com.example.demo.DTO.WebSocketDepartmentCaseDTO(c.id, c.complaint, c.date, c.department) " +
            "FROM Case c " +
            "WHERE c.requiresSecondOpinion = true AND c.summaryDiagnosis IS NOT NULL")
    List<WebSocketDepartmentCaseDTO> findNeededReviewDepartmentCases();

    @Query("SELECT new com.example.demo.DTO.WebSocketDepartmentCaseDTO(c.id, c.complaint, c.date, c.department) " +
            "FROM Case c " +
            "WHERE c.requiresSecondOpinion = true AND c.summaryDiagnosis IS NOT NULL AND c.department = :department " +
            "AND c.meetingDate IS NULL AND NOT ((c.consultingDoctor IS NOT NULL AND c.consultText IS NULL) OR (c.consultingDoctor IS NULL AND c.consultText IS NOT NULL))"
    )
    List<WebSocketDepartmentCaseDTO> findDepartmentNeededReviewDepartmentCases(@Param("department") String department);


}

