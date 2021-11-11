package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.jpa.school.entity.Student;
import com.example.schoolmanagement.jpa.system.entity.User;
import com.example.schoolmanagement.jpa.school.StudentRepository;
import com.example.schoolmanagement.jpa.system.UserRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log
@Controller
public class StudentController {
    //    private UserService userService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Value("${spring.jpa.defautPageSize}")
    private int defaultPagesize;

    @ModelAttribute
    public void populateModel(ModelMap model, Authentication authentication) {

        Student student;
        student = model.containsAttribute("student") ? (Student) model.get("student") : new Student();
        model.addAttribute("student", student);
    }
    @GetMapping("/students")
    public String goPagedStudent() {
        return "redirect:/students/1/"+defaultPagesize;
    }
    @GetMapping("/students/{pageNo}/{pageSize}")
    public String ListStudents(@PathVariable Map<String, String> pathVarsMap,
                               Model model) {
        int pageNo = (pathVarsMap.get("pageNo") == null)?1: Integer.parseInt(pathVarsMap.get("pageNo"));
        int pageSize = (pathVarsMap.get("pageSize") == null)?defaultPagesize: Integer.parseInt(pathVarsMap.get("pageSize"));
        Pageable pageable = PageRequest.of(pageNo-1,pageSize);
        Page<Student> pages =  studentRepository.findAll(pageable);
        List<Student> students = pages.getContent();
        List<Integer> pageNums =  new ArrayList<Integer>();
        if(pageNo > 1) pageNums.add(pageNo-1);
        pageNums.add(pageNo);
        if(pageNo < pages.getTotalPages()) pageNums.add(pageNo+1);
        model.addAttribute("students",students);
        model.addAttribute("totalPages",pages.getTotalPages());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalItems",pages.getTotalElements());
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("pageNums",pageNums);

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

        Student student = studentRepository.getStudentById(id);
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
        Student student = studentRepository.getStudentById(id);
        studentRepository.delete(student);
        return "redirect:/students";
    }
}
