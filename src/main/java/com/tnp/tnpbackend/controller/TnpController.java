package com.tnp.tnpbackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tnp.tnpbackend.dto.StudentDTO;
import com.tnp.tnpbackend.dto.StudentSummaryDTO;
import com.tnp.tnpbackend.service.StudentService;

@RestController
@RequestMapping("/tnp/head")
public class TnpController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getAllStudents")
    public ResponseEntity<?> getAllStudents() {
        List<StudentSummaryDTO> students = studentService.getAllStudents();
        //System.out.println(students);
        if(students.isEmpty() || students == null){
            return ResponseEntity.status(404).body("No students found.");
        }
        return ResponseEntity.ok(students);
    }

    //for getting student by id
    @GetMapping("/getStudent/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") String id){
        if(id == null || id.isEmpty()){
            return ResponseEntity.badRequest().body("Invalid ID provided.");
        }
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    //for getting students by department
    @GetMapping("/getStudentsByDepartment/{department}")
    public ResponseEntity<?> getStudentsByDepartment(@PathVariable("department") String department) {
        List<StudentSummaryDTO> students = studentService.getStudentsByDepartment(department);
        if (students.isEmpty()) {
            return ResponseEntity.status(404).body("No students found in the specified department.");
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/getDepartment")
    public ResponseEntity<?> getDepartment(){
        List<String>departments = studentService.findDistinctDepartments();
        return ResponseEntity.ok(departments);
    }
}
