package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.entity.SchoolClass;
import com.example.schoolmanagement.entity.Student;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repo.SchoolClassRepository;
import com.example.schoolmanagement.repo.StudentRepository;
import com.example.schoolmanagement.repo.TeacherRepository;
import com.example.schoolmanagement.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class SchoolClassController {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SchoolClassRepository schoolClassRepository;
    @Autowired
    private TeacherRepository teachacherRepository;
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void populateModel(ModelMap model, Authentication authentication) {
        User user;
        user = model.containsAttribute("auth") ? (User) model.get("user") : userRepository.findByUsername(authentication.getName());
        SchoolClass schoolClass;
        schoolClass = model.containsAttribute("class") ? (SchoolClass) model.get("class") : new SchoolClass();
        model.addAttribute("auth", user);
        model.addAttribute("class", schoolClass);
    }
    @GetMapping("/classes")
    public String ListClasses(Model model) {
        List<SchoolClass> schoolClasses = schoolClassRepository.findAll();
        model.addAttribute("classes",schoolClasses);
        return "classes";
    }
    @GetMapping("/classes/new")
    public String newOrUpdateClass(Model model) {
        return "class";
    }
}
