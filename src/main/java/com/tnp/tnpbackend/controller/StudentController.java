package com.tnp.tnpbackend.controller;

import java.io.IOException;
import java.util.List;

import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tnp.tnpbackend.dto.ApplicationRequest;
import com.tnp.tnpbackend.dto.StudentDTO;
import com.tnp.tnpbackend.model.Student;
import com.tnp.tnpbackend.service.PlacementService;
import com.tnp.tnpbackend.service.RecruiterService;
import com.tnp.tnpbackend.service.StudentService;

@RestController
@RequestMapping("/tnp/student")
public class StudentController {

  @Autowired
  private StudentService studentService;

  @Autowired
  private PlacementService placementService;

  @Autowired
  private RecruiterService recruiterService;

  @PostMapping("/addStudent")
  public ResponseEntity<?> addStudent(@RequestBody StudentDTO studentDTO) {
    StudentDTO savStudentDTO = studentService.addStudent(studentDTO);
    return ResponseEntity.ok(savStudentDTO);
  }

  @GetMapping("/dashboard")
  public String studentDashboard() {
    return "Student logged in";
  }

  @PatchMapping("/update-profile")
<<<<<<< HEAD
  public ResponseEntity<?> updateProfile(@RequestBody StudentDTO studentDTO,
  @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
    StudentDTO updatedstudentDTO = studentService.updateProfile(studentDTO,profileImage);
=======
  public ResponseEntity<?> updateProfile(@RequestBody StudentDTO studentDTO) {
    StudentDTO updatedstudentDTO = studentService.updateProfile(studentDTO);
>>>>>>> 8ece429 (Added profile-upload and dynamic filtering for student)
    return ResponseEntity.ok(updatedstudentDTO);

  }

  @PostMapping(value = "/upload-profile-pic")
    public ResponseEntity<?> uploadProfilePic(
            @RequestPart(value = "profileImage", required = true) MultipartFile profileImage) throws IOException {
        try {
            StudentDTO updatedStudentDTO = studentService.updateProfilePicture(profileImage);
            return ResponseEntity.ok(updatedStudentDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Failed to upload profile picture: " + e.getMessage());
        }
    }

  @PostMapping("/apply")
  public ResponseEntity<String> applyToRecruiter(@RequestBody ApplicationRequest request) {
    placementService.applyToRecruiter(request.getStudentId(), request.getRecruiterId());
    return ResponseEntity.ok("Application submitted");
  }

  @GetMapping("/recruiter/{recruiterId}/students")
  public ResponseEntity<List<Student>> getStudentsForRecruiter(@PathVariable String recruiterId) {
    return ResponseEntity.ok(placementService.getStudentsForRecruiter(recruiterId));
  }

  @GetMapping("/all-companies")
  public ResponseEntity<List<?>> getAllCompanies() {
    return ResponseEntity.ok(recruiterService.getAllRecruiters());
  }
}