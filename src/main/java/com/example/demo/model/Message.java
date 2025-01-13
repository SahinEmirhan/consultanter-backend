package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "complaintId")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_id")
    @JsonIgnore
    private Complaint complaintId;

    @Column(updatable=false, name = "user_type")
    private String userType;

    public Message(String message, Complaint complaintId, String userType) {
        this.message = message;
        this.complaintId = complaintId;
        this.userType = userType;
    }

}
