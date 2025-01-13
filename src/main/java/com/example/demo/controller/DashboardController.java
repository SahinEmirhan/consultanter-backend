package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private HttpSession session;

    public boolean isAdmin() {
        String userType = (String) session.getAttribute("userType");
        return "ADMIN".equals(userType);
    }

    public boolean isLoggedIn() {
        Long userId = (Long) session.getAttribute("userId");
        return userId != null;
    }


    public boolean isPatient() {
        String userType = (String) session.getAttribute("userType");
        return "PATIENT".equals(userType);
    }

    public boolean isDoctor() {
        String userType = (String) session.getAttribute("userType");
        return "DOCTOR".equals(userType);
    }
}
