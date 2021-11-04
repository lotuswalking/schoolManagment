package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.Services.UserService;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repo.StudentRepository;
import com.example.schoolmanagement.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {
//    private UserService userService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/students")
    public String ListStudents(Model model, Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("students",studentRepository.findAll());
        model.addAttribute("auth",user);
        return "students";
    }
}
