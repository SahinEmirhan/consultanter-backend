package com.example.demo.service;

import com.example.demo.DTO.PatientDtoIU;
import com.example.demo.DTO.PatientUserFullDTO;
import com.example.demo.model.Patient;
import com.example.demo.repository.PatientRepository;
import com.fasterxml.jackson.databind.util.BeanUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient registerPatient(Patient patient) {

        return patientRepository.save(patient);
    }

    public PatientUserFullDTO getPatientById(Long id) {
        return patientRepository.findPatientFullDetailsById(id);
    }

    public void updatePatient(Long sessionId , PatientDtoIU patient){
        Patient dbPatient =getPatient(sessionId);

        BeanUtils.copyProperties(patient , dbPatient);
        patientRepository.save(dbPatient);
    }

    public void buyTicket(Long sessionId , Integer ticketSize){
            //ticketSize sıfırdan küçük girilirse sıkıntı validationda bakılmalı.
            if(ticketSize > 0){
                Patient dbPatient = getPatient(sessionId);
                ticketSize += dbPatient.getTickets();
                dbPatient.setTickets(ticketSize);
                patientRepository.save(dbPatient);
                return;
            }
            throw new RuntimeException("ticket size must be greater than 0");
    }

    public void useTicket(Long sessionId){
        Patient dbPatient = getPatient(sessionId);
        Integer dbTickets = dbPatient.getTickets();
        if(dbTickets > 0){
            dbPatient.setTickets(--dbTickets);
            patientRepository.save(dbPatient);
            return;
        }
        throw new RuntimeException("Ticket size is insufficient.");
    }

    protected Patient getPatient(Long id){
        Optional<Patient> optional = patientRepository.findById(id);
        if(optional.isPresent())
            return optional.get();

        throw new RuntimeException("Patient Record Not Found.");
    }




}
