package com.example.demo.controller;

import com.example.demo.DTO.CaseDetailDTO;
import com.example.demo.DTO.PatientDtoIU;
import com.example.demo.DTO.PatientUserFullDTO;
import com.example.demo.DTO.TemporaryFileDTO;
import com.example.demo.model.Patient;
import com.example.demo.service.CaseService;
import com.example.demo.service.PatientService;
import com.example.demo.service.TemporaryFileService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.nio.file.FileStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patient")
@PreAuthorize("@dashboardController.isPatient()")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private TemporaryFileService temporaryFileService;

    @Autowired
    private CaseService caseService;

    @PostMapping("/createFile")
    public ResponseEntity<TemporaryFileDTO> createTemporaryFile(@Valid @RequestBody TemporaryFileDTO temporaryFileDTO, HttpSession session) {
        Long patientId = (Long) session.getAttribute("userId");
        TemporaryFileDTO createdTempFile = temporaryFileService.createTemporaryFile(temporaryFileDTO, patientId);
        return new ResponseEntity<>(createdTempFile, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getPatientProfile(HttpSession session) {
        PatientUserFullDTO patient = patientService.getPatientById((Long) session.getAttribute("userId"));
        return ResponseEntity.ok(patient);
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String , Object>> updatePatient(@RequestBody PatientDtoIU patient, HttpSession session ){

        Long sessionId = (Long)session.getAttribute("userId");
        patientService.updatePatient(sessionId , patient);

        Map<String , Object> response = new HashMap<>();
        response.put("success" , true);
        response.put("message" , "Information has been successfully updated.");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/buy/ticket")
    public ResponseEntity<Map<String , Object>> buyTicket( @RequestBody Map<String,Integer> ticket , HttpSession session){
        Long sessionId = (Long)session.getAttribute("userId");
        patientService.buyTicket(sessionId , ticket.get("tickets"));

        Map<String , Object> response = new HashMap<>();
        response.put("success" , true);
        response.put("message" , "The purchase was successfully completed.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/use/ticket")
    public ResponseEntity<Map<String , Object>> useTicket(HttpSession session){

        Long sessionId = (Long)session.getAttribute("userId");
        patientService.useTicket(sessionId);

        Map<String , Object> response = new HashMap<>();
        response.put("success" , true);
        response.put("message" , "The ticket was successfully used.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/case/{id}/detail")
    public ResponseEntity<?> getCaseDetails(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        CaseDetailDTO caseDetail = caseService.getCaseDetail(id);

        return ResponseEntity.status(HttpStatus.OK).body(caseDetail);
    }




}