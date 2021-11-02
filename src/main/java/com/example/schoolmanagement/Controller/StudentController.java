package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.Services.UserService;
import com.example.schoolmanagement.repo.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
//    private UserService userService;
    private StudentRepository studentRepository;
    @GetMapping("/students")
    public String ListStudents(Model model) {
        model.addAttribute("students",studentRepository.findAll());
        return "students";
    }
}
