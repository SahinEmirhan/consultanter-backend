package com.example.demo.DTO;

import com.example.demo.model.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CaseDetailDTO {

    private String complaint;

    private LocalDate date;

    private Double height;

    private Double weight;

    private String knownConditions;

    private String department;

    private String summaryDiagnosis;

    private String detailedDiagnosis;

    private boolean requiresSecondOpinion;

    private String consultText;

    private String consultingDoctor;

    private Date meetingDate;

    private boolean rate;

    private List<FileDocumentDTO> documents;

}
