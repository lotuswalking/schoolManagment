package com.example.schoolmanagement.jpa.schoolAdmin;

import com.example.schoolmanagement.jpa.school.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepositoryDto extends JpaRepository<Student, Long> {
    Student findByEmail(String email);
    Student getStudentById(Long id);
}
