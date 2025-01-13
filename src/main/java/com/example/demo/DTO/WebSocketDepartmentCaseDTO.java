package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketDepartmentCaseDTO {

    private Long id;

    private String complaint;

    private LocalDate date;

    private String department;
}