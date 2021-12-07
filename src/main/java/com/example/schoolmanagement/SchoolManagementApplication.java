package com.example.schoolmanagement;

import com.example.schoolmanagement.jpa.school.StudentRepository;
import com.example.schoolmanagement.jpa.school.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableScheduling
@Configuration
//@EntityScan({"com.example.schoolmanagement.jpa.school.entity",
//"com.example.schoolmanagement.jpa.system.entity"})
public class SchoolManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);
    }

    @Bean
    public TaskScheduler springTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Bean(name="resourceList")
    public List<HashMap> MyConfigList()
    {
        List<HashMap> myConfigList = new ArrayList<>();
        HashMap map = new HashMap();
        map.put("config1", "value1");
        myConfigList.add(map);
        return myConfigList;
    }


}
