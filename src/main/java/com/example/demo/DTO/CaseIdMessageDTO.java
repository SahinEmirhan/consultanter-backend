package com.example.demo.DTO;

public class CaseIdMessageDTO {

    private Long id;

    public CaseIdMessageDTO(){

    }

    public CaseIdMessageDTO(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
