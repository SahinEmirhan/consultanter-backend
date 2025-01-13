package com.example.demo.DTO;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDoctorDTO extends RegisterUserDTO{

    @NotNull(message = "profile photo must be uploaded")
    private MultipartFile profilePhoto;

    @NotNull(message = "tax plate document must be uploaded")
    private MultipartFile taxPlate;

    @NotNull(message = "certificate photo must be uploaded")
    private MultipartFile certificatePhoto;

    @NotNull(message = "degree photo must be uploaded")
    private MultipartFile degreePhoto;
}
