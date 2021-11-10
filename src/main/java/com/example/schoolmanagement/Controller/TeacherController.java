package com.example.schoolmanagement.Controller;


import com.example.schoolmanagement.jpa.school.entity.Teacher;
import com.example.schoolmanagement.jpa.system.entity.User;

import com.example.schoolmanagement.jpa.school.TeacherRepository;
import com.example.schoolmanagement.jpa.system.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Log
@Controller
public class TeacherController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @ModelAttribute
    public void populateModel(ModelMap model, Authentication authentication) {

    }
    @GetMapping("/teachers")
    public String Listteachers(Model model) {
        model.addAttribute("teachers", teacherRepository.findAll());
        return "teachers";
    }

    @GetMapping("/teachers/new")
    public String newteachers(Model mode) {
        Teacher teacher = new Teacher();
        mode.addAttribute("teacher", teacher);
        mode.addAttribute("curMode", "new");
        return "teacher";
    }

    @GetMapping("/teachers/edit/{id}")
    public String updateteacher(Model mode, @PathVariable Long id) {
        Teacher teacher = teacherRepository.getTeacherById(id);
        mode.addAttribute("teacher", teacher);
        mode.addAttribute("curMode", "edit");
        return "teacher";
    }

    @PostMapping(value = "/teachers/save")
    public String saveteacher(@RequestParam(value = "curMode", required = true) String CurMode,
                              @ModelAttribute Teacher teacher) {

        teacherRepository.save(teacher);
        return "redirect:/teachers";
    }
    @DeleteMapping(value = "/teachers/remove/{id}")
    public String removeteacher(@PathVariable Long id) {
        Teacher teacher = teacherRepository.getTeacherById(id);
        teacherRepository.delete(teacher);
        return "redirect:/teachers";
    }
}
