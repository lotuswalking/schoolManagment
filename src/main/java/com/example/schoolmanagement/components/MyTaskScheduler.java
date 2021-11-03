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
        myService.addRole("ADMIN");
        myService.addRole("USER");
        myService.addUser("user");
        myService.addUser("admin");
        myService.addGroup("admin");
        myService.addRoleToUser("user","USER");
        myService.addRoleToUser("admin","ADMIN");
        myService.addStudents("junyan","li","lotuswalking@gmail.com");
        myService.addStudents("cicheng","li","cicheng.l@gmail.com");
        myService.addStudents("ritch","chen","ritch.chen@gmail.com");
        log.info("*********Finished to run task initDatabase");
    }

}
