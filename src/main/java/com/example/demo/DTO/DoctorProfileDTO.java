package com.example.demo.DTO;

import com.example.demo.model.Comment;
import com.example.demo.model.Rate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorProfileDTO {
    private String name;
    private String surname;
    private String profilePhoto;
    private String specialization;
    private int doctorType;
    private List<Short> rates;
    private List<String> comments;
    private String country;

}
