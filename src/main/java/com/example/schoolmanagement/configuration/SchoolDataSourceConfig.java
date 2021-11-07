package com.example.schoolmanagement.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.example.schoolmanagement.jpa.school",
entityManagerFactoryRef = "schoolEntityManagerFactory",
transactionManagerRef = "schoolTransactionManager")
public class SchoolDataSourceConfig {

    @Bean(name="schoolDataSourceProperties")
    @ConfigurationProperties("school.datasource")
    public DataSourceProperties  schoolDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "schoolDataSource")
    @ConfigurationProperties("school.datasource.configuration")
    public DataSource schoolDataSource(@Qualifier("schoolDataSourceProperties") DataSourceProperties schoolDataSourceProperties) {
        return schoolDataSourceProperties.initializeDataSourceBuilder().build();
    }


//    @ConfigurationProperties("spring.jpa")
    private HashMap<String,Object> jpaProperties() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    private String[] packeAges() {
        return new String[]{"com.example.schoolmanagement.repo.system",
                "com.example.schoolmanagement.jpa.school.entity"};
    }

    @Bean(name = "schoolEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean schoolEntityManagerFactory(
           @Qualifier("schoolDataSource") DataSource primaryDataSource,
           EntityManagerFactoryBuilder primaryEntityManagerFactoryBuilder) {

        HashMap<String, Object> properties = new HashMap<>();
        return primaryEntityManagerFactoryBuilder
                .dataSource(primaryDataSource)
                .packages(packeAges())
                .persistenceUnit("schoolDataSource")
                .properties(jpaProperties())
                .build();
    }


    @Bean(name = "schoolTransactionManager")
    public PlatformTransactionManager schoolTransactionManager(
            @Qualifier("schoolEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {

        return new JpaTransactionManager(primaryEntityManagerFactory);
    }

}
