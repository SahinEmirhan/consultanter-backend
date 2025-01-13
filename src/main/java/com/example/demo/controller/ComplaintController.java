package com.example.demo.controller;

import com.example.demo.model.Complaint;
import com.example.demo.model.Message;
import com.example.demo.service.ComplaintService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaint")
@PreAuthorize("@dashboardController.isLoggedIn()")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createComplaint(@RequestBody Map<String,String> complaint, HttpSession session) {
        Map<String, Object> response = new HashMap<>();

        Long userId = (Long) session.getAttribute("userId");

        Complaint returnedComplaint = complaintService.createComplaint(complaint.get("message"), userId);

        response.put("success", true);
        response.put("message", returnedComplaint.getText());
        response.put("id", returnedComplaint.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/close/{id}")
    public ResponseEntity<Map<String, Object>> setComplaintToClose(@PathVariable("id") Long id) {
        Map<String, Object> response = new HashMap<>();

        complaintService.setComplaintStatusToClose(id);

        response.put("success", true);
        response.put("message", "confirmed");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/messages/{id}")
    public List<Message> getComplaintMessages(@PathVariable("id") Long id) {
        List<Message> msg =complaintService.getAllMessages(id);
        return msg;
    }

    @GetMapping("/myComplaints")
    public List<Complaint> getMyComplaints(HttpSession session){
        Long id = (Long) session.getAttribute("userId");

        List<Complaint> complaints =complaintService.getAllComplaint(id);

        return complaints;
    }

}