package com.example.schoolmanagement.Controller;


import com.example.schoolmanagement.jpa.school.entity.Student;
import com.example.schoolmanagement.jpa.school.entity.Teacher;
import com.example.schoolmanagement.jpa.system.entity.User;

import com.example.schoolmanagement.jpa.school.TeacherRepository;
import com.example.schoolmanagement.jpa.system.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log
@Controller
public class TeacherController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Value("${spring.jpa.defautPageSize}")
    private int defaultPagesize;

    @ModelAttribute
    public void populateModel(ModelMap model, Authentication authentication) {

    }
    @GetMapping("/teachers")
    public String Listteachers(Model model) {
//        model.addAttribute("teachers", teacherRepository.findAll());
        return "redirect:/teachers/1/"+defaultPagesize;
    }
    @GetMapping("/teachers/{pageNo}/{pageSize}")
    public String ListTeachers(@PathVariable Map<String, String> pathVarsMap,
                               Model model) {
        int pageNo = (pathVarsMap.get("pageNo") == null)?1: Integer.parseInt(pathVarsMap.get("pageNo"));
        int pageSize = (pathVarsMap.get("pageSize") == null)?defaultPagesize: Integer.parseInt(pathVarsMap.get("pageSize"));
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        Page<Teacher> pages =  teacherRepository.findAll(pageable);
        List<Teacher> teachers = pages.getContent();
        List<Integer> pageNums =  new ArrayList<Integer>();
        if(pageNo > 1) pageNums.add(pageNo-1);
        pageNums.add(pageNo);
        if(pageNo < pages.getTotalPages()) pageNums.add(pageNo+1);
        model.addAttribute("teachers",teachers);
        model.addAttribute("totalPages",pages.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalItems",pages.getTotalElements());
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("pageNums",pageNums);

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
    @GetMapping(value = "/teachers/remove/{id}")
    public String removeteacher(@PathVariable Long id) {
        Teacher teacher = teacherRepository.getTeacherById(id);
        teacherRepository.delete(teacher);
        return "redirect:/teachers";
    }
}
