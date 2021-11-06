package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.jpa.school.entity.Student;
import com.example.schoolmanagement.jpa.system.entity.User;
import com.example.schoolmanagement.jpa.school.StudentRepository;
import com.example.schoolmanagement.jpa.system.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


@Controller
public class StudentController {
    //    private UserService userService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void populateModel(ModelMap model, Authentication authentication) {

        Student student;
        student = model.containsAttribute("student") ? (Student) model.get("student") : new Student();
        model.addAttribute("student", student);
    }

    @GetMapping("/students")
    public String ListStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "students";
    }

    @GetMapping("/students/new")
    public String newStudents(Model mode) {
        Student student = new Student();
        mode.addAttribute("student", student);
        mode.addAttribute("curMode", "new");
        return "student";
    }

    @GetMapping("/students/edit/{id}")
    public String updateStudent(Model mode, @PathVariable Long id) {
        Student student = studentRepository.getById(id);
        mode.addAttribute("student", student);
        mode.addAttribute("curMode", "edit");
        return "student";
    }

    @PostMapping(value = "/students/save")
    public String saveStudent(@RequestParam(value = "curMode", required = true) String CurMode,
                              @ModelAttribute Student student) {

        studentRepository.save(student);
        return "redirect:/students";
    }
    @GetMapping(value = "/students/remove/{id}")
    public String removeStudent(@PathVariable Long id) {
        Student student = studentRepository.getById(id);
        studentRepository.delete(student);
        return "redirect:/students";
    }
}
