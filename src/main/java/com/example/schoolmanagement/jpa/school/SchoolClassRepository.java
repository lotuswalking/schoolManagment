package com.example.schoolmanagement.jpa.school;

import com.example.schoolmanagement.jpa.school.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
}
