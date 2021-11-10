package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.jpa.RestEntity.GitUser;
import com.example.schoolmanagement.util.ApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
