package com.example.demo.service;

import com.example.demo.DTO.FileDocumentDTO;
import com.example.demo.DTO.TemporaryFileDTO;
import com.example.demo.DTO.TemporaryFileDetailDTO;
import com.example.demo.DTO.WebSocketDepartmentTemporaryFileDTO;
import com.example.demo.model.Doctor;
import com.example.demo.model.FileDocument;
import com.example.demo.model.TemporaryFile;
import com.example.demo.model.User;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.FileDocumentRepository;
import com.example.demo.repository.TemporaryFileRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TemporaryFileService {

    @Autowired
    private TemporaryFileRepository temporaryFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileDocumentRepository fileDocumentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private LocalFileService localFileService;

    @Autowired
    private PatientService patientService;

    public TemporaryFileDTO createTemporaryFile(TemporaryFileDTO temporaryFileDTO, Long patientId) {

        if (patientId == null) {
            throw new RuntimeException("Patient ID is null");
        }

        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        patientService.useTicket(patientId);


        TemporaryFile tempFile = new TemporaryFile.Builder()
                .setComplaint(temporaryFileDTO.getComplaint())
                .setDate(temporaryFileDTO.getDate())
                .setDepartment()
                .setHeight(temporaryFileDTO.getHeight())
                .setWeight(temporaryFileDTO.getWeight())
                .setKnownConditions(temporaryFileDTO.getKnownConditions())
                .setPatient(patient)
                .build();

        /*
        TemporaryFile tempFile = new TemporaryFile(
                patient,
                temporaryFileDTO.getComplaint(),
                temporaryFileDTO.getDate(),
                temporaryFileDTO.getHeight(),
                temporaryFileDTO.getWeight(),
                temporaryFileDTO.getKnownConditions()
        );

         */

        TemporaryFile savedTempFile = temporaryFileRepository.save(tempFile);

        return new TemporaryFileDTO(
                savedTempFile.getId(),
                savedTempFile.getPatient().getId(),
                savedTempFile.getComplaint(),
                savedTempFile.getDate(),
                savedTempFile.getHeight(),
                savedTempFile.getWeight(),
                savedTempFile.getKnownConditions()
        );
    }

    public List<TemporaryFileDTO> getTemporaryFilesWithNullDepartment() {
        return temporaryFileRepository.findByDepartmentIsNull().stream()
                .map(tempFile -> new TemporaryFileDTO(
                        tempFile.getId(),
                        tempFile.getPatient().getId(),
                        tempFile.getComplaint(),
                        tempFile.getDate(),
                        tempFile.getHeight(),
                        tempFile.getWeight(),
                        tempFile.getKnownConditions()
                ))
                .collect(Collectors.toList());
    }


    public List<WebSocketDepartmentTemporaryFileDTO> getTemporaryFilesWithNotNullDepartment() {
        return temporaryFileRepository.findByDepartmentIsNotNull().stream()
                .map(tempFile -> new WebSocketDepartmentTemporaryFileDTO(
                        tempFile.getId(),
                        tempFile.getComplaint(),
                        tempFile.getDate(),
                        tempFile.getDepartment()
                ))
                .collect(Collectors.toList());
    }

    public String updateDepartment(Long id, String department) {
        TemporaryFile temporaryFile = temporaryFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TemporaryFile kaydı bulunamadı"));

        temporaryFile.setDepartment(department);

        temporaryFileRepository.save(temporaryFile);


        return "success";
    }



    @Transactional
    public TemporaryFileDetailDTO getTemporaryFileDetail(Long id) {
        TemporaryFile temporaryFile = temporaryFileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TemporaryFile kaydı bulunamadı"));

        List<FileDocument> documents = fileDocumentRepository.findByFileIdAndType(id, "document");



        List<FileDocumentDTO> documentDTOs = documents.stream()
                .map(doc -> {
                    try {
                        return new FileDocumentDTO(
                                doc.getId(),
                                doc.getFileId(),
                                doc.getType(),
                                Base64.getEncoder().encodeToString(StreamUtils.copyToByteArray(localFileService.loadFileAsResource(doc.getFileData()).getInputStream()))
                        );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        TemporaryFileDetailDTO detailDTO = new TemporaryFileDetailDTO();
        detailDTO.setId(temporaryFile.getId());
        detailDTO.setPatientId(temporaryFile.getPatient().getId());
        detailDTO.setComplaint(temporaryFile.getComplaint());
        detailDTO.setDate(temporaryFile.getDate());
        detailDTO.setHeight(temporaryFile.getHeight());
        detailDTO.setWeight(temporaryFile.getWeight());
        detailDTO.setKnownConditions(temporaryFile.getKnownConditions());
        detailDTO.setDepartment(temporaryFile.getDepartment());
        detailDTO.setDocuments(documentDTOs);

        return detailDTO;
    }


    public List<TemporaryFileDTO> getTemporaryFilesWithDoctorDepartment(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));

        String doctorDepartment = doctor.getSpecialization();

        if (doctorDepartment == null) {
            throw new RuntimeException("Doktorun department bilgisi bulunamadı");
        }


        List<TemporaryFile> temporaryFiles = temporaryFileRepository.findByDepartment(doctorDepartment);



        return temporaryFiles.stream()
                .map(tempFile -> new TemporaryFileDTO(
                        tempFile.getId(),
                        tempFile.getPatient().getId(),
                        tempFile.getComplaint(),
                        tempFile.getDate(),
                        tempFile.getHeight(),
                        tempFile.getWeight(),
                        tempFile.getKnownConditions()
                ))
                .collect(Collectors.toList());

    }

}
