package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.Services.MyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
@Log
@Controller
@RequiredArgsConstructor
public class homeController {


    private final MyService myService;
    @Resource(name="studentsMap")
    List<HashMap> studentsMaps;

    @GetMapping("/")
    public String index() {
        return "home";
    }
    @GetMapping("/home")
    public  String home(){
        studentsMaps.forEach(e -> {
            log.info(e.get("email").toString());
        });
        return "home";
    }

    @GetMapping("/hello")
    public  String hello(){
        return "hello";
    }

}
