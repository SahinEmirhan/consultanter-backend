package com.example.demo.service;

import com.example.demo.model.Complaint;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import com.example.demo.repository.ComplaintRepository;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public Complaint createComplaint(String text, Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("There is no user"));

        return complaintRepository.save(new Complaint(text, user));
    }

    @Transactional
    public Message sendMessage(String text, Long complaintId, String userType) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new RuntimeException("We cannot find a spesific complaint"));

        Message message = new Message(text, complaint, userType);

        messageRepository.save(message);

        return message;

    }

    public void setComplaintStatusToClose(Long complaintId) {
        Complaint complaint = complaintRepository.findById(complaintId).orElseThrow(() -> new RuntimeException("WE cannot find a spesific complaint"));

        complaint.setClosed(true);

        complaintRepository.save(complaint);
    }

    public List<Message> getAllMessages(Long complaintId) {
        return messageRepository.findByComplaintId_Id(complaintId);
    }

    public List<Complaint> getAllComplaint(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("There is no user"));
        List<Complaint> complaints =complaintRepository.findByComplaintUserAndClosedFalse(user);
        return complaints;
    }

    public List<Complaint> getAdminAllComplaint() {
        return complaintRepository.findByClosedFalse();
    }

}
