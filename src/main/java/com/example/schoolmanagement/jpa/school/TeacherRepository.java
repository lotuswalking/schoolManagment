package com.example.schoolmanagement.jpa.school;

import com.example.schoolmanagement.jpa.school.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByEmail(String email);
    Teacher getTeacherById(Long id);
}
