package com.example.schoolmanagement.components;

import com.example.schoolmanagement.Services.MyService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component("MyTaskScheduler")
@Log
public class MyTaskScheduler {
    @Autowired
    private MyService myService;
//    @Scheduled(cron="0 20 0 * * *")  //20 minutes after midnight
    @Scheduled(fixedDelay = 1000*60*60)
    public void initDatabase() {
        log.info("*********Start to run task initDatabase");
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


        myService.addStudents("junyan","li","lotuswalking@gmail.com");
        myService.addStudents("cicheng","li","cicheng.l@gmail.com");
        myService.addStudents("ritch","chen","ritch.chen@gmail.com");
        log.info("*********Finished to run task initDatabase");
    }


}
