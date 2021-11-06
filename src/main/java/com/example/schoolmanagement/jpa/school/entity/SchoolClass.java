package com.example.schoolmanagement.jpa.school.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="School_Classes")
public class SchoolClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="class_id")
    private Long id;
    @Column(name = "class_name", nullable = false)
    private String classname;
    @OneToMany(mappedBy="schoolClass")
    private List<Student> students = new ArrayList<>();
    @OneToMany(mappedBy="schoolClass")
    private List<Teacher> teachers = new ArrayList<>();


}
