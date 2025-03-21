package com.tnp.tnpbackend.dto;

import java.time.LocalDate;
import java.util.List;

import com.tnp.tnpbackend.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {


    private int studentId;
    private String studentName;
    private String userName;
    private String password;
    private String email;
    private double cgpa;
    private int department;
    private List<String> skills;
    private String resumeURL;
    private String academicYear;
    private int backlogs;
    private int graduationYear;
    private String contactNumber;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Role role;



}
