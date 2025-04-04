package com.tnp.tnpbackend.serviceImpl;

import com.tnp.tnpbackend.exception.NoDataFoundException;
import com.tnp.tnpbackend.model.Recruiter;
import com.tnp.tnpbackend.model.Student;
import com.tnp.tnpbackend.model.StudentRecruiterRelation;
import com.tnp.tnpbackend.repository.RecruiterRepository;
import com.tnp.tnpbackend.repository.StudentRecruiterRelationRepository;
import com.tnp.tnpbackend.repository.StudentRepository;
import com.tnp.tnpbackend.service.AdminService;
import com.tnp.tnpbackend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private StudentRecruiterRelationRepository relationRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private StudentService studentService;

    @Override
    public Map<String, Long> getApplicationStatusAnalytics() {
        List<StudentRecruiterRelation> relations = relationRepository.findAll();
        if (relations.isEmpty()) {
            throw new NoDataFoundException("No application data found for analytics");
        }
        Map<String, Long> statusCounts = new HashMap<>();

        statusCounts.put("APPLIED", relations.stream().filter(r -> "APPLIED".equals(r.getStatus())).count());
        statusCounts.put("INTERVIEWED", relations.stream().filter(r -> "INTERVIEWED".equals(r.getStatus())).count());
        statusCounts.put("HIRED", relations.stream().filter(r -> "HIRED".equals(r.getStatus())).count());

        return statusCounts;
    }

    @Override
    public Map<String, Long> getStudentsByDepartmentAnalytics() {
        List<String> departments = studentService.findDistinctDepartments();
        if(departments.isEmpty()){
            throw new NoDataFoundException("No application data found for analytics");
        }
        Map<String, Long> departmentCounts = new HashMap<>();

        for (String dept : departments) {
            long count = studentRepository.findByDepartment(dept).size();
            departmentCounts.put(dept, count);
        }

        return departmentCounts;
    }

    @Override
    public Map<String, Double> getPlacementSuccessRateAnalytics() {
        List<String> departments = studentService.findDistinctDepartments();
        if(departments.isEmpty()){
            throw new NoDataFoundException("No application data found for analytics");
        }
        Map<String, Double> successRates = new HashMap<>();

        for (String dept : departments) {
            List<Student> deptStudents = studentRepository.findByDepartment(dept);
            if (deptStudents.isEmpty()) {
                throw new NoDataFoundException("No application data found for placement success rate analytics");
            }
            long totalStudents = deptStudents.size();
            if (totalStudents == 0) {
                continue;
            }

            long hiredCount = relationRepository.findAll().stream()
                    .filter(r -> "HIRED".equals(r.getStatus()) && dept.equals(r.getStudent().getDepartment()))
                    .count();

            double successRate = (double) hiredCount / totalStudents * 100;
            successRates.put(dept, Math.round(successRate * 100.0) / 100.0); // Round to 2 decimals
        }

        return successRates;
    }

    @Override
    public List<Map<String, Object>> getTopRecruitersAnalytics() {
        Map<Recruiter, Long> hireCounts = relationRepository.findAll().stream()
                .filter(r -> "HIRED".equals(r.getStatus()))
                .collect(Collectors.groupingBy(StudentRecruiterRelation::getRecruiter, Collectors.counting()));

        return hireCounts.entrySet().stream()
                .sorted(Map.Entry.<Recruiter, Long>comparingByValue().reversed())
                .limit(5) // Top 5 recruiters
                .map(entry -> {
                    Map<String, Object> recruiterData = new HashMap<>();
                    recruiterData.put("companyName", entry.getKey().getCompanyName());
                    recruiterData.put("hiredCount", entry.getValue());
                    return recruiterData;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Map<String, Long>> getRecruiterApplicationAnalytics() {
        List<Recruiter> recruiters = recruiterRepository.findAll();
        if (recruiters.isEmpty()) {
            throw new NoDataFoundException("No application data found for analytics");
        }
        Map<String, Map<String, Long>> recruiterStats = new HashMap<>();

        for (Recruiter recruiter : recruiters) {
            List<StudentRecruiterRelation> relations = relationRepository.findByRecruiter(recruiter);
            Map<String, Long> statusCounts = new HashMap<>();

            statusCounts.put("APPLIED", relations.stream().filter(r -> "APPLIED".equals(r.getStatus())).count());
            statusCounts.put("INTERVIEWED",
                    relations.stream().filter(r -> "INTERVIEWED".equals(r.getStatus())).count());
            statusCounts.put("HIRED", relations.stream().filter(r -> "HIRED".equals(r.getStatus())).count());

            recruiterStats.put(recruiter.getCompanyName(), statusCounts);
        }

        return recruiterStats;
    }
}