package com.example.schoolmanagement.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

/****************************
1.这是用于配置多数据库的配置文件,需要在application.yml上面写下如下内容
 spring:
 datasource:
     url: jdbc:mysql://${MYSQL_HOST:localhost}:3306/securitydb
     username: root
     password: 123456
 jpa:
    hibernate.hbm2ddl.auto: update
 2.Repository不需要跟entity在一个包里面,但是这个程序里面需要将所有使用这个dbcontext的对方都引用进来 builder.packages(packeAges)
************************ */

@Configuration
@EnableJpaRepositories(basePackages = {"com.example.schoolmanagement.jpa.system"},  //这个配置为这个包所在的repository配置
        entityManagerFactoryRef = "systemEntityManagerFactory",                //关联下文中的bean,用使用创建的数据库连接
        transactionManagerRef = "systemTransactionManager")                 //关联下文中的Bean,将systemEntityManagerFactory送到JPA
public class SystemDataSourceConfig {

    private String[] packeAges= {"com.example.schoolmanagement.repo.system",
            "com.example.schoolmanagement.jpa.system.entity"};

    @Bean(name="systemDataSourceProperties")
    @Primary
    @ConfigurationProperties("spring.datasource")   //读取application.yml内容
    public DataSourceProperties schoolDataSourceProperties() {
        return new DataSourceProperties();
    }
    @Primary
    @Bean(name = "systemDataSource")
    @ConfigurationProperties("spring.datasource.configuration")  //这个不太理解,感觉用不上
    public DataSource systemDataSource(@Qualifier("systemDataSourceProperties") DataSourceProperties systemDataSourceProperties) {
        return systemDataSourceProperties.initializeDataSourceBuilder().build();
    }
//    @Bean(name="systemJpaProperties")    //这个Bean是我自己定义的,用于自动创建数据库的表
//    @ConfigurationProperties("spring.jpa")
    private HashMap<String,Object> jpaProperties() {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }


    @Primary
    @Bean(name = "systemEntityManagerFactory")   //创建数据库连接
    public LocalContainerEntityManagerFactoryBean system1EntityManagerFactory(
            EntityManagerFactoryBuilder primaryEntityManagerFactoryBuilder,
            @Qualifier("systemDataSource") DataSource systemDataSource
            ) {

        return primaryEntityManagerFactoryBuilder
                .dataSource(systemDataSource)
                .packages(packeAges)
                .persistenceUnit("systemDataSource")
                .properties(jpaProperties())
                .build();
    }

    @Primary
    @Bean(name = "systemTransactionManager")
    public PlatformTransactionManager systemTransactionManager(
            @Qualifier("systemEntityManagerFactory") EntityManagerFactory primaryEntityManagerFactory) {

        return new JpaTransactionManager(primaryEntityManagerFactory);
    }
}
