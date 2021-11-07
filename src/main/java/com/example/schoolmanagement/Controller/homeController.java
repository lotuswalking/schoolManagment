package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.Services.MyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class homeController {


    private final MyService myService;

    @GetMapping("/")
    public String index() {
        return "home";
    }
    @GetMapping("/home")
    public  String home(){
        return "home";
    }

    @GetMapping("/hello")
    public  String hello(){
        return "hello";
    }

}
