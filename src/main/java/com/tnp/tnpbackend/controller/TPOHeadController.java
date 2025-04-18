package com.tnp.tnpbackend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tnp.tnpbackend.dto.RecruiterDTO;
import com.tnp.tnpbackend.dto.StudentDTO;
import com.tnp.tnpbackend.helper.AdminHelper;
import com.tnp.tnpbackend.model.Student;
import com.tnp.tnpbackend.serviceImpl.AdminExcelServiceImpl;
import com.tnp.tnpbackend.serviceImpl.StudentServiceImpl;

@RestController
@RequestMapping("/tnp/tpo-head")
public class TPOHeadController {

    @Autowired
    private AdminHelper adminHelper;

    @Autowired
    private AdminExcelServiceImpl adminExcelServiceImpl;

    @Autowired
    private StudentServiceImpl studentService;

    public ResponseEntity<?> addCompany(@RequestBody RecruiterDTO recruiterDTO) {
        if (recruiterDTO == null)
            return null;
        try {
            
        } catch (Exception e) {
        }
        return null;
    }

    // upload students in bulk
    @PostMapping("/upload-students")
    public ResponseEntity<?> addStudents(@RequestParam("file") MultipartFile file) {
        System.out.println("Received file: " + file.getOriginalFilename());
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded or file is empty.");
        }
        if (!adminHelper.checkExcelFormat(file)) {
            return ResponseEntity.badRequest().body("Invalid file format. Please upload an Excel file (.xlsx).");
        }
        try {
            List<Student> students = adminHelper.convertExcelToListOfStudents(file.getInputStream());
            if (students.isEmpty()) {
                return ResponseEntity.ok("No valid student data found in the file.");
            }
            adminExcelServiceImpl.save(students);
            return ResponseEntity.ok("Successfully uploaded " + students.size() + " students.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error processing file: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error saving students: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    // for single student data upload
    @PostMapping(value = "/upload-student", consumes = "application/x-www-form-urlencoded", produces = "application/json")
    public ResponseEntity<?> addStudent(@RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("graduationYear") String graduationYear, @RequestParam("department") String department) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setUsername(username);
        studentDTO.setPassword(password);
        studentDTO.setGraduationYear(graduationYear);
        studentDTO.setDepartment(department);
        System.out.println(studentDTO);
        StudentDTO savedStudentDTO = studentService.addStudent(studentDTO);
        return ResponseEntity.ok(savedStudentDTO);
    }
}
