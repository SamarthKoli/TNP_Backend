package com.tnp.tnpbackend.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tnp.tnpbackend.service.AppUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "student")
public class Student implements AppUser{

    @Id
    private String studentId;
    private String studentName;
    private String username;
    private String password;
    private String email;
    private double cgpa;
    private String department;
    private List<String> skills;
    private String resumeURL;
    private String academicYear;
    private int backlogs;
    private String graduationYear;
    private String contactNumber;
    private LocalDate createdAt;
    private LocalDateTime updatedAt;
    
    private String role;

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getRole() {
        return this.role;
    }
}
