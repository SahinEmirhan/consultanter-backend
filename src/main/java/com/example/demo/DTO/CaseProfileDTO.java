package com.example.demo.DTO;

import java.time.LocalDate;

public class CaseProfileDTO {

    private Long id;

    private String complaint;

    private LocalDate date;

    public CaseProfileDTO(){
    }


    public CaseProfileDTO(Long id, String complaint, LocalDate date) {
        this.id = id;
        this.complaint = complaint;
        this.date = date;
    }

    public Long getId() {
        return this.id;
    }

    public String getComplaint(){
        return this.complaint;
    }

    public LocalDate getDate() {
        return this.date;
    }

}
