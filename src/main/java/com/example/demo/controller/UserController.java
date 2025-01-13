package com.example.demo.controller;

import com.example.demo.DTO.RegisterAdminDTO;
import com.example.demo.DTO.RegisterDoctorDTO;
import com.example.demo.DTO.RegisterUserDTO;
import com.example.demo.model.Admin;
import com.example.demo.model.Doctor;
import com.example.demo.model.Patient;
import com.example.demo.service.AdminService;
import com.example.demo.service.AuthService;
import com.example.demo.service.DoctorService;
import com.example.demo.service.PatientService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AuthService authService;

    @GetMapping("/userType")
    @PreAuthorize("@dashboardController.isLoggedIn()")
    public ResponseEntity<Map<String, Object>> userType(HttpSession session){

        Map<String, Object> response = new HashMap<>();

        response.put("success", true);
        response.put("userType",(String) session.getAttribute("userType"));

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            HttpSession session,
            HttpServletRequest request
    ) throws NoSuchAlgorithmException {
        Map<String, Object> response = new HashMap<>();
        Boolean isLoginSuccessful = authService.login(email, password, session);

        if (isLoginSuccessful) {
            response.put("success", true);
            response.put("message", "Successfully logged in");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        authService.logout(session);
        return "Logged out successfully";
    }




    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerPatient(@Valid @RequestBody RegisterUserDTO userRequest) {
        Map<String, Object> response = new HashMap<>();


            LocalDate parsedBirthDate = LocalDate.parse(userRequest.getBirthDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            Patient newUser = new Patient.Builder()
                    .setPassword(userRequest.getPassword())
                    .setEmail(userRequest.getEmail())
                    .setName(userRequest.getName())
                    .setSurname(userRequest.getSurname())
                    .setPhone(userRequest.getPhone())
                    .setBirthDate(parsedBirthDate)
                    .setGender(userRequest.getGender())
                    .setCountry(userRequest.getCountry())
                    .setCity(userRequest.getCity())
                    .setTickets()
                    .build();



            patientService.registerPatient(newUser);

            response.put("success", true);
            response.put("message", "Patient successfully registered");

            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

    }

    @PostMapping("/admin/register")
    public ResponseEntity<Map<String, Object>> registerAdmin(@Valid @RequestBody RegisterAdminDTO userRequest) {
        Map<String, Object> response = new HashMap<>();

            LocalDate parsedBirthDate = LocalDate.parse(userRequest.getBirthDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            Admin newUser = new Admin.Builder()
                    .setPassword(userRequest.getPassword())
                    .setEmail(userRequest.getEmail())
                    .setName(userRequest.getName())
                    .setSurname(userRequest.getSurname())
                    .setPhone(userRequest.getPhone())
                    .setBirthDate(parsedBirthDate)
                    .setGender(userRequest.getGender())
                    .setCountry(userRequest.getCountry())
                    .setCity(userRequest.getCity())
                    .setLegalCapability(true)
                    .build();

            adminService.registerAdmin(newUser);

            response.put("success", true);
            response.put("message", "Admin successfully registered");
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

    }

    private static final List<String> ACCEPTED_FILE_TYPES = List.of("image/jpeg", "image/jpg", "image/png", "application/pdf");

    @PostMapping("/doctor/register")
    public ResponseEntity<Map<String, Object>> registerDoctor(@Valid @ModelAttribute RegisterDoctorDTO userRequest) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Doktor klasör yolunu belirle
            Path doctorDirectory = Paths.get("doctors");
            Files.createDirectories(doctorDirectory);  // Klasör yoksa oluştur

            String email = userRequest.getEmail();

            String profilePhotoPath = saveFile(userRequest.getProfilePhoto(), doctorDirectory, email);
            String degreePhotoPath = saveFile(userRequest.getDegreePhoto(), doctorDirectory, email);
            String taxPlatePath = saveFile(userRequest.getTaxPlate(), doctorDirectory, email);
            String certificatePhotoPath = saveFile(userRequest.getCertificatePhoto(), doctorDirectory, email);

            LocalDate parsedBirthDate = LocalDate.parse(userRequest.getBirthDate(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));

            Doctor newUser = new Doctor.Builder()
                    .setPassword(userRequest.getPassword())
                    .setEmail(email)
                    .setName(userRequest.getName())
                    .setSurname(userRequest.getSurname())
                    .setPhone(userRequest.getPhone())
                    .setBirthDate(parsedBirthDate)
                    .setGender(userRequest.getGender())
                    .setCountry(userRequest.getCountry())
                    .setCity(userRequest.getCity())
                    .setAdminApproval()
                    .setCertificatePhoto(certificatePhotoPath)
                    .setProfilePhoto(profilePhotoPath)
                    .setDegreePhoto(degreePhotoPath)
                    .setTaxPlate(taxPlatePath)
                    .build();

            doctorService.registerDoctor(newUser);

            response.put("success", true);
            response.put("message", "Doctor successfully registered");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Error while saving files: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    private byte[] checkFileTypeAndGetBytes(MultipartFile file, String fieldName) {
        String contentType = file.getContentType();

        if (!ACCEPTED_FILE_TYPES.contains(contentType)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    fieldName + " türü geçersiz: " + contentType + ". Sadece JPEG ve PNG kabul edilir.");
        }

        try {
            return file.getBytes();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Dosya okuma sırasında bir hata oluştu: " + e.getMessage());
        }
    }


    private String saveFile(MultipartFile file, Path directory, String email) throws IOException {
        if (file != null && !file.isEmpty()) {
            // Orijinal dosya adını koruyarak email ekle
            String originalFileName = file.getOriginalFilename();
            String fileName = email + "-" + originalFileName;  // email-dosyaAdi.jpg
            Path filePath = directory.resolve(fileName);
            Files.write(filePath, file.getBytes());
            return filePath.toString();  // Dosya yolunu döndür
        }
        return null;
    }


}
