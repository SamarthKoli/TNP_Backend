package com.tnp.tnpbackend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tnp.tnpbackend.model.Student;


@Repository
public interface StudentRepository extends MongoRepository<Student, Integer> {

    Optional<Student> findByUserName(String username);

    Boolean existsByUserName(String username);
    
}
