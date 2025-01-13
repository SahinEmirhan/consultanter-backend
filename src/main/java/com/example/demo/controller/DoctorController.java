package com.example.demo.controller;


import com.example.demo.DTO.*;
import com.example.demo.model.FileDocument;
import com.example.demo.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/doctor")
@PreAuthorize("@dashboardController.isDoctor()")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private TemporaryFileService temporaryFileService;

    @Autowired
    private CaseService caseService;

    @Autowired
    private FileDownloadService fileDownloadService;

    @Autowired
    private LocalFileService localFileService;

    @GetMapping("/profile")
    public ResponseEntity<?> getDoctorProfile(HttpSession session) throws IOException {

        DoctorUserFullDTO doctor = doctorService.getDoctorById((Long) session.getAttribute("userId"));
        doctor.setProfilePhoto(Base64.getEncoder().encodeToString(StreamUtils.copyToByteArray(localFileService.loadFileAsResource(doctor.getProfilePhoto()).getInputStream())));


        return ResponseEntity.ok(doctor);

    }

    @GetMapping("/temporaryFile")
    public ResponseEntity<?> getTemporaryFile(HttpSession session) {

            Long userId = (Long) session.getAttribute("userId");

            List<TemporaryFileDTO> temporaryFiles = temporaryFileService.getTemporaryFilesWithDoctorDepartment(userId);

            if (temporaryFiles.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No temporary files found for the specified doctor department");
            }
            return ResponseEntity.ok(temporaryFiles);
    }


    @GetMapping("/{id}/detail")
    public ResponseEntity<?> getTemporaryFileDetail(@PathVariable Long id) {

            TemporaryFileDetailDTO detailDTO = temporaryFileService.getTemporaryFileDetail(id);
            return ResponseEntity.ok(detailDTO);

    }


    @GetMapping("/case/{id}/detail")
    public ResponseEntity<?> getCaseDetails(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        CaseDetailDTO caseDetail = caseService.getCaseDetail(id);

        return ResponseEntity.status(HttpStatus.OK).body(caseDetail);
    }


    @GetMapping("/case/{id}/detail/dicom")
    public ResponseEntity<?> getDicomFiles(@PathVariable Long id) {

        List<FileDocument> files = fileDownloadService.getDicomFiles(id);

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }



    @PostMapping("/make-diagnosis/{id}")
    public  ResponseEntity<?> makeDiagnosis(@PathVariable(name = "id") Long caseId , @RequestBody CaseDiagnosisDtoIU caseDiagnosisDtoIU , HttpSession session){
        Long sessionId = (Long)session.getAttribute("userId");

        doctorService.makeDiagnosis(caseId , caseDiagnosisDtoIU , sessionId);

        Map<String , Object> response = new HashMap<>();
        response.put("success" , true);
        response.put("message" , "tanı koyma işlemi başarılı.");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @GetMapping("/reviewCases")
    public List<WebSocketDepartmentCaseDTO> getReviewCases(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");

        return caseService.getDepartmentNeededReviewCase(userId);

    }





}

