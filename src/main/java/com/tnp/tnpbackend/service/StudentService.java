package com.tnp.tnpbackend.service;

import com.tnp.tnpbackend.dto.StudentApplicationHistoryDTO;
import com.tnp.tnpbackend.dto.StudentDTO;
import com.tnp.tnpbackend.dto.StudentSummaryDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    StudentDTO addStudent(StudentDTO studentDTO);

    StudentDTO updateProfile(StudentDTO studentDTO);

    StudentDTO updateProfilePicture(MultipartFile profileImage) throws IOException;

    List<StudentSummaryDTO> getAllStudents();

    StudentDTO getStudentById(String id);

    List<StudentSummaryDTO> getStudentsByDepartment(String department);

    List<String> findDistinctDepartments();

    StudentDTO getStudentByDepartmentAndId(String department, String studentId);

    List<StudentApplicationHistoryDTO> getMyHistory(String studentId);

    void updateApplicationStatus(String relationId, String newStatus);
}