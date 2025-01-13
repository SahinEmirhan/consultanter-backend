package com.example.demo.service;

import com.example.demo.DTO.CaseDiagnosisDtoIU;
import com.example.demo.DTO.DoctorUserDTO;
import com.example.demo.DTO.DoctorUserFullDTO;
import com.example.demo.model.Case;
import com.example.demo.model.Doctor;
import com.example.demo.model.User;
import com.example.demo.repository.CaseRepository;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private CaseRepository caseRepository;

    public Doctor registerDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    /*
    @Transactional
    public byte[] getProfilePhoto(Long doctorId) {
        return doctorRepository.findProfilePhotoById(doctorId);
    }


     */


    public DoctorUserFullDTO getDoctorById(Long id) {
        return doctorRepository.findDoctorFullDetailsById(id);
    }

    public List<DoctorUserDTO> getDoctorsAwaitingApproval() {
        return doctorRepository.findByAdminApprovalFalse();
    }
    public boolean approveDoctor(Long id, String specialization, int doctorType) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(id);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            doctor.setAdminApproval();
            doctor.setSpecialization(specialization);
            doctor.setDoctorType(doctorType);
            doctorRepository.save(doctor);
            return true;
        }
        return false;
    }

    public String getAuthorizedDepartmentsForDoctor(Long id) {
        Optional<Doctor> doctorOpt = doctorRepository.findById(id);
        if (doctorOpt.isPresent()) {
            Doctor doctor = doctorOpt.get();
            return doctor.getSpecialization();
        }
        return "";
    }

    public void makeDiagnosis(Long caseId , CaseDiagnosisDtoIU caseDiagnosisDtoIU , Long sessionId){
        Case dbCase = caseRepository.findById(caseId).orElseThrow(() -> new RuntimeException("There is no file case."));

        if(dbCase.getDoctor().getId() == sessionId){
            dbCase.setSummaryDiagnosis(caseDiagnosisDtoIU.getSummaryDiagnosis());
            dbCase.setDetailedDiagnosis(caseDiagnosisDtoIU.getDetailedDiagnosis());
            dbCase.setMeetingDate(caseDiagnosisDtoIU.getMeetingDate());
            dbCase.setConsultText(null);
            dbCase.setConsultingDoctor(null);
            caseRepository.save(dbCase);
            return;
        }
        throw new RuntimeException("You don't have permission to perform the file.");
    }


}
