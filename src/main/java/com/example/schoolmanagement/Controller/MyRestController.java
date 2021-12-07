package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.jpa.RestEntity.GitUser;
import com.example.schoolmanagement.utility.ApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log
public class MyRestController {
    private final String baseUrl = "https://api.github.com/users";
    @GetMapping("/gitusers")
    public List<GitUser> listGitUsers() throws JsonProcessingException {
        List<GitUser> gitUsers = ApiClient.exchange(baseUrl,GitUser.class);
        return  gitUsers;

    }

}
