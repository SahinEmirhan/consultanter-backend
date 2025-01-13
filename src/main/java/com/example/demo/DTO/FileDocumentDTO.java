package com.example.demo.DTO;

public class FileDocumentDTO {
    private Long id;
    private Long fileId;
    private String type;
    private String filePath;


    public FileDocumentDTO(Long id, Long fileId, String type, String filePath) {
        this.id = id;
        this.fileId = fileId;
        this.type = type;
        this.filePath = filePath;
    }

    // Getter ve Setter metodları
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
