package com.example.schoolmanagement.jpa.school;

import com.example.schoolmanagement.jpa.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
    Student getStudentById(Long id);
}
