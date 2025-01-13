package com.example.demo.repository;

import com.example.demo.model.Complaint;
import com.example.demo.model.Message;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends  JpaRepository<Complaint, Long>{
    public List<Complaint> findByComplaintUserAndClosedFalse(User user);

    public List<Complaint> findByClosedFalse();
}