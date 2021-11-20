package com.example.schoolmanagement.utility;

import com.example.schoolmanagement.jpa.RestEntity.GitUser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ApiClient {
    public static List<GitUser> exchange(String url, Class responseType) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object[]> responseEntity =restTemplate.getForEntity(url,Object[].class);
        Object[] objects = responseEntity.getBody();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE,false);
        return Arrays.stream(objects)
                .map(object -> mapper.convertValue(object, GitUser.class))
                .collect(Collectors.toList());
    }
    public static List<Object> exchangeObjects(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object[]> responseEntity =restTemplate.getForEntity(url,Object[].class);
        Object[] objects = responseEntity.getBody();
        return Arrays.stream(objects)
                .collect(Collectors.toList());
    }

}
