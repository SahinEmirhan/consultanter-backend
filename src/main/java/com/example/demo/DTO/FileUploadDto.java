package com.example.demo.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileUploadDto {
    private String fileId;
    private String type;
    private MultipartFile file;
}
