package com.example.schoolmanagement.configuration;

import com.example.schoolmanagement.jpa.school.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class StudentStore {
    @Autowired
    StudentRepository studentRepository;

    @Bean(name="studentsMap")
    public List<HashMap> StudentsMaps() {
        List<HashMap> studentList = new ArrayList<HashMap>();
        studentList = studentRepository.findAll()
                .stream()
                .map(student -> {
                    HashMap map = new HashMap();
                    map.put("email",student.getEmail());
                    map.put("student",student);
                    return map;
                })
                .collect(Collectors.toList());
        return studentList;


    }
}
