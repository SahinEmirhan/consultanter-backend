package com.example.demo.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {

    @NotBlank(message = "password must be written")
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$",
            message = "Password must be 8-20 characters long, and include at least one uppercase letter, one lowercase letter, and one digit"
    )
    private String password;

    @Email(message = "Email is not correct form")
    @NotBlank(message = "Email must be written")
    private String email;

    @NotBlank(message = "name must be written")
    private String name;

    @NotBlank(message = "surname must be written")
    private String surname;

    @NotBlank(message = "phone must be written")
    private String phone;

    @NotBlank(message = "birth date must be written")
    private String birthDate;

    @NotBlank(message = "gender must be written")
    @Pattern(regexp = "^(male|female)$", message = "Gender must be either 'Male' or 'Female'")
    private String gender;

    @NotBlank(message = "country must be written")
    private String country;

    @NotBlank(message = "city must be written")
    private String city;
}
