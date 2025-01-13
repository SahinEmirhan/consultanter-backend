package com.example.demo.controller;


import com.example.demo.DTO.*;
import com.example.demo.model.Complaint;
import com.example.demo.model.Department;
import com.example.demo.model.Doctor;
import com.example.demo.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("@dashboardController.isAdmin()")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TemporaryFileService temporaryFileService;

    @Autowired
    private LocalFileService localFileService;

    @Autowired
    private ComplaintService complaintService;

    @GetMapping("/doctors/pending-approval")
    public ResponseEntity<Map<String, Object>> getDoctorsAwaitingApproval() {
        Map<String, Object> response = new HashMap<>();
            List<DoctorUserDTO> doctor = doctorService.getDoctorsAwaitingApproval();

            response.put("success", true);
            response.put("data", doctor);

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

    }

    @GetMapping("/doctorDetailed/{id}")
    public DoctorUserFullDTO getDoctorById(@PathVariable Long id) throws IOException {

        DoctorUserFullDTO doctorGeneral =doctorService.getDoctorById(id);

        doctorGeneral.setProfilePhoto(Base64.getEncoder().encodeToString(StreamUtils.copyToByteArray(localFileService.loadFileAsResource(doctorGeneral.getProfilePhoto()).getInputStream())));
        doctorGeneral.setCertificatePhoto(Base64.getEncoder().encodeToString(StreamUtils.copyToByteArray(localFileService.loadFileAsResource(doctorGeneral.getCertificatePhoto()).getInputStream())));
        doctorGeneral.setDegreePhoto(Base64.getEncoder().encodeToString(StreamUtils.copyToByteArray(localFileService.loadFileAsResource(doctorGeneral.getDegreePhoto()).getInputStream())));
        doctorGeneral.setTaxPlate(Base64.getEncoder().encodeToString(StreamUtils.copyToByteArray(localFileService.loadFileAsResource(doctorGeneral.getTaxPlate()).getInputStream())));

        return doctorGeneral;

    }


    @PostMapping("/approve/{id}")
    public ResponseEntity<Map<String, Object>> approveDoctor(
            @PathVariable Long id,
            @RequestBody Map<String, Object> userRequest
    ) {
        Map<String, Object> response = new HashMap<>();

            boolean isApproved = doctorService.approveDoctor(id, (String) userRequest.get("specialization"), Integer.parseInt((String) userRequest.get("doctorType")));


            if (isApproved) {

                response.put("success", true);
                response.put("message", "Doctor approved successfully");

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

            } else {
                response.put("success", false);
                response.put("message", "Doctor not found");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

            }

    }


    @PostMapping("/department")
    public ResponseEntity<Map<String, Object>> createDepartment(@RequestBody Map<String, Object> userRequest) {
        Map<String, Object> response = new HashMap<>();


            Department department = departmentService.createDepartment((String) userRequest.get("name"));
            response.put("success", true);
            response.put("message", "Department successfully created!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);


    }

    @GetMapping("/department")
    public ResponseEntity<List<DepartmentDTO>> getDepartment() {
        return ResponseEntity.ok( departmentService.getAllDepartments());
    }

    @GetMapping("/nurseFiles")
    public ResponseEntity<?> getFilesWithNullDepartment() {

            List<TemporaryFileDTO> files = temporaryFileService.getTemporaryFilesWithNullDepartment();
            if (files.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Department alanı null olan kayıt bulunamadı.");
            }
            return ResponseEntity.ok(files);

    }


    @PostMapping("/update-department")
    public ResponseEntity<?> updateDepartment(@Valid @RequestBody TemporaryFileDepartmentDTO departmentDTO) {
        if (departmentDTO.getId() == null || departmentDTO.getDepartment() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID veya Department boş olamaz");
            }

            String updatedFile = temporaryFileService.updateDepartment(departmentDTO.getId(), departmentDTO.getDepartment());
            return ResponseEntity.ok(updatedFile);


    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<?> getTemporaryFileDetail(@PathVariable Long id) {

            TemporaryFileDetailDTO detailDTO = temporaryFileService.getTemporaryFileDetail(id);
            return ResponseEntity.ok(detailDTO);

    }

    @GetMapping("/complaints")
    public List<Complaint> getAdminComplaints() {
        return complaintService.getAdminAllComplaint();
    }



}
