package com.example.schoolmanagement.components;

import com.example.schoolmanagement.Services.MyService;
import com.example.schoolmanagement.jpa.school.entity.Student;
import com.example.schoolmanagement.jpa.school.entity.Teacher;
import com.example.schoolmanagement.utility.ApiClient;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("MyTaskScheduler")
@Log
public class MyTaskScheduler {
    @Autowired
    private MyService myService;

    private final String baseUrl = "https://api.github.com/users";
//    @Scheduled(cron="0 20 0 * * *")  //20 minutes after midnight
    @Scheduled(fixedDelay = 1000*60*60)
    public void initDatabase() {
        log.info("*********Start to run task initDatabase");
        log.info("*********Users Roles Groups");
        myService.addRole(Long.valueOf(1),"ROLE_USER");
        myService.addRole(Long.valueOf(2),"ROLE_ADMIN");
        myService.addUser(Long.valueOf(1),"user");
        myService.addUser(Long.valueOf(2),"admin");
        myService.addGroup(Long.valueOf(1),"userGroup");
        myService.addGroup(Long.valueOf(2),"adminGroup");

        myService.mapUserToGroup(Long.valueOf(1),"user","userGroup");
        myService.mapUserToGroup(Long.valueOf(2),"admin","adminGroup");

        myService.mapGroupToRole(Long.valueOf(2),"userGroup","ROLE_USER");
        myService.mapGroupToRole(Long.valueOf(2),"adminGroup","ROLE_ADMIN");

        log.info("*********Students");
//        String userUri = "file://d/java/workplace/schoolManagment/src/main/resources/templates/students.json";
        String userUri = "http://localhost:8080/json/students.json";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,true);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE,false);
        List<Object> objects = ApiClient.exchangeObjects(userUri);
        objects.stream()
                .map(object -> mapper.convertValue(object,Student.class))
                .collect(Collectors.toList())
                .forEach(student -> myService.addStudent(student));
        log.info("*********Teacher");
        userUri = "http://localhost:8080/json/teachers.json";
        objects = ApiClient.exchangeObjects(userUri);
        objects.stream()
                .map(object -> mapper.convertValue(object, Teacher.class))
                .collect(Collectors.toList())
                .forEach(teacher -> myService.addTeacher(teacher));

        log.info("*********Finished to run task initDatabase");

    }



}
