package com.example.schoolmanagement.repo;

import com.example.schoolmanagement.entity.Role;
import com.example.schoolmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
}
