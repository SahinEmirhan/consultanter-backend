package com.example.demo.service;

import com.example.demo.DTO.*;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import javax.print.Doc;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CaseService {

    @Autowired
    private TemporaryFileRepository temporaryFileRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileDocumentRepository fileDocumentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private LocalFileService localFileService;

    @Transactional
    public Long handleTemporaryFile(Long caseId, Long docId) {
        TemporaryFile temporaryFile = temporaryFileRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("TemporaryFile not found for ID: " + caseId));

        User doctor = userRepository.findById(docId)
                .orElseThrow(() -> new RuntimeException("Doctor not found for ID: " + docId));

        Doctor doctorType = doctorRepository.findById(doctor.getId())
                .orElseThrow(() -> new RuntimeException("Doctor not found for ID: " + docId));
        boolean requestOpinion = false;
        if(doctorType.getDoctorType()%2 ==0) {
            requestOpinion = true;
        }

        Case newCase = new Case.Builder()
                .setId(temporaryFile.getId())
                .setComplaint(temporaryFile.getComplaint())
                .setPatient(temporaryFile.getPatient())
                .setDoctor(doctor)
                .setDate(temporaryFile.getDate())
                .setHeight(temporaryFile.getHeight())
                .setWeight(temporaryFile.getWeight())
                .setKnownConditions(temporaryFile.getKnownConditions())
                .setDepartment(temporaryFile.getDepartment())
                .setDetailedDiagnosis()
                .setSummaryDiagnosis()
                .setRequiresSecondOpinion(requestOpinion)
                .build();

        caseRepository.save(newCase);

        temporaryFileRepository.delete(temporaryFile);

        return caseId;
    }

    public List<CaseProfileDTO> mineCasesAsPatient(Long id) {
        Optional<User> patient = userRepository.findById(id);
        if (patient.isPresent()) {
            User patient_now = patient.get();

            return caseRepository.findMineCasesAsDTOAsPatient(patient_now);

        } else {
            throw new EntityNotFoundException("Patient not found with id: " + id);
        }

    }

    public List<CaseProfileDTO> mineCasesAsDoctor(Long id) {
        Optional<User> doctor = userRepository.findById(id);
        if(doctor.isPresent()) {
            User doctor_now = doctor.get();
            return caseRepository.findMineCasesAsDTOAsDoctor(doctor_now);
        }else {
            throw new EntityNotFoundException("Doctor not found with id: " + id);
        }
    }

    public CaseDetailDTO getCaseDetail(Long id) {
        Case caseDetail = caseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Case kaydı bulunamadı"));


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

        CaseDetailDTO detailDTO = new CaseDetailDTO();
        detailDTO.setComplaint(caseDetail.getComplaint());
        detailDTO.setDate(caseDetail.getDate());
        detailDTO.setHeight(caseDetail.getHeight());
        detailDTO.setWeight(caseDetail.getWeight());
        detailDTO.setKnownConditions(caseDetail.getKnownConditions());
        detailDTO.setDepartment(caseDetail.getDepartment());
        detailDTO.setDocuments(documentDTOs);
        detailDTO.setRequiresSecondOpinion(caseDetail.isRequiresSecondOpinion());
        detailDTO.setDetailedDiagnosis(caseDetail.getDetailedDiagnosis());
        detailDTO.setSummaryDiagnosis(caseDetail.getSummaryDiagnosis());
        detailDTO.setConsultText(caseDetail.getConsultText());
        detailDTO.setMeetingDate(caseDetail.getMeetingDate());
        if(caseDetail.getConsultingDoctor() != null) {
            detailDTO.setConsultingDoctor(caseDetail.getConsultingDoctor().getName());
        }
        detailDTO.setRate(false);
        if(caseDetail.getRate() != null) {
            detailDTO.setRate(true);
        }


        return detailDTO;
    }

    public void commentCase(Long sessionId, Long caseId , String message){
        //patientId , doctorId
        Case dbCase = caseRepository.findById(caseId).orElseThrow(() -> new RuntimeException("Dosya bulunamadı."));
        if(dbCase.getPatient().getId() == sessionId){
            Comment comment = new Comment();
            comment.setMessage(message);
            commentRepository.save(comment);

            Doctor caseDoctor = doctorRepository.findById(dbCase.getDoctor().getId()).orElseThrow(() -> new RuntimeException("Dosya ile ilgili doktor bulunamadı."));
            caseDoctor.addComment(comment);
            doctorRepository.save(caseDoctor);

            dbCase.setComment(comment);
            dbCase.setClosure(true);
            caseRepository.save(dbCase);
            return;

        }

        throw new RuntimeException("Dosyanın sahibi bu kullanıcı değil.");
    }

    public void rateCase(Long sessionId , Long caseId , Short rating){
        Case dbCase = caseRepository.findById(caseId).orElseThrow(() -> new RuntimeException("Dosya bulunamadı."));

        if(dbCase.getPatient().getId() == sessionId){
            Rate rate = new Rate();
            rate.setRating(rating);
            rateRepository.save(rate);

            dbCase.setRate(rate);
            dbCase.setClosure(true);
            caseRepository.save(dbCase);

            Doctor caseDoctor = doctorRepository.findById(dbCase.getDoctor().getId()).orElseThrow(() -> new RuntimeException("Dosya ile ilgili doktor bulunamadı."));
            caseDoctor.addRate(rate);
            doctorRepository.save(caseDoctor);
            return;
        }

        throw new RuntimeException("Dosyanın sahibi bu kullanıcı değil.");
    }

    @Transactional
    public void reviewCase(CaseReviewDTO caseReview, Long sessionId) {
        Case updatedCase = caseRepository.findById(caseReview.getCaseId()).orElseThrow(() -> new RuntimeException("There is no available case"));
        Doctor doctor = doctorRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("There is no doctor according to sessionId"));
        doctor.incrementReviewedCase();

        if(caseReview.isReviewResult()) {
            User user = userRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("There is no user"));
            updatedCase.setConsultingDoctor(user);
        } else {
            updatedCase.setConsultText(caseReview.getReviewText());
            updatedCase.setSummaryDiagnosis("");
        }

        caseRepository.save(updatedCase);
    }

    public List<WebSocketDepartmentCaseDTO> getNeededReviewCase() {

        return caseRepository.findNeededReviewDepartmentCases().stream()
                .map(caseFile -> new WebSocketDepartmentCaseDTO(
                        caseFile.getId(),
                        caseFile.getComplaint(),
                        caseFile.getDate(),
                        caseFile.getDepartment()
                ))
                .collect(Collectors.toList());
    }

    public List<WebSocketDepartmentCaseDTO> getDepartmentNeededReviewCase(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doktor bulunamadı"));

        String doctorDepartment = doctor.getSpecialization();

        return caseRepository.findDepartmentNeededReviewDepartmentCases(doctorDepartment);
    }

    public DoctorProfileDTO findDoctorByCaseId(Long caseId) throws IOException {
        Long doctorId = caseRepository.findDoctorIdByCaseId(caseId);
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("doktor bulunamadı."));
        DoctorProfileDTO doctorProfileDTO = new DoctorProfileDTO();
        List<Short> rateList = new ArrayList<>();
        List<String> commentList = new ArrayList<>();
        Integer rating = 0;
        for(Rate rate : doctor.getRates()){
            rateList.add(rate.getRating());
        }
        for(Comment comment : doctor.getComments()){
            commentList.add(comment.getMessage());
        }
        doctorProfileDTO.setName(doctor.getName());
        doctorProfileDTO.setSurname(doctor.getSurname());
        doctorProfileDTO.setDoctorType(doctor.getDoctorType());
        doctorProfileDTO.setProfilePhoto( Base64.getEncoder().encodeToString(StreamUtils.copyToByteArray(localFileService.loadFileAsResource(doctor.getProfilePhoto()).getInputStream())));
        doctorProfileDTO.setSpecialization(doctor.getSpecialization());
        doctorProfileDTO.setComments(commentList);
        doctorProfileDTO.setRates(rateList);
        doctorProfileDTO.setCountry(doctor.getCountry());
        return doctorProfileDTO;

    }

}
