package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.Services.MyService;
import com.example.schoolmanagement.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class homeController {


    private final MyService myService;

    @GetMapping("/")
    public String home() {
        myService.addRole("ADMIN");
        myService.addRole("USER");
        myService.addUser("user");
        myService.addUser("admin");
        myService.addGroup("admin");
        myService.addRoleToUser("user","USER");
        myService.addRoleToUser("admin","ADMIN");

        return "home";
    }

}
