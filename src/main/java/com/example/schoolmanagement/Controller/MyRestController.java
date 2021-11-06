package com.example.schoolmanagement.Controller;

import com.example.schoolmanagement.jpa.RestEntity.GitUser;
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
    private RestTemplate restTemplate = new RestTemplate();
    @GetMapping("/gitusers")
    public List<GitUser> listGitUsers() throws JsonProcessingException {
        List<GitUser> gitUsers = exchange(baseUrl,GitUser.class);
        return  gitUsers;

    }
    private List<GitUser> exchange(String url,  Class responseType) {
        ResponseEntity<Object[]> responseEntity =restTemplate.getForEntity(url,Object[].class);
        Object[] objects = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE,false);
       return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, GitUser.class))
                .collect(Collectors.toList());
//        return  gitUsers;
    }
}
