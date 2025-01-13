package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complaint_user_id")
    @JsonIgnore
    private User complaintUser;
    private String text;

    private boolean closed;

    public Complaint(String text, User user) {
        this.text = text;
        this.complaintUser = user;
        this.closed = false;
    }

}
