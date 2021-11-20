package com.example.schoolmanagement.security;


import com.example.schoolmanagement.Services.UserService;
import com.example.schoolmanagement.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtFilter jwtFilter;

    @Value("${custom.config.sign.method}")  //==database
    private String signMethod;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if(!signMethod.equalsIgnoreCase("jwt")) {
            http.csrf().disable();
            http.authorizeRequests().antMatchers("/", "/home","/login","/api/login/**","/static/**","/json/**").permitAll();
            http.authorizeRequests().anyRequest().authenticated();
            http.formLogin().defaultSuccessUrl("/students").failureForwardUrl("/home");
        }else{
            http.csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/home","/login","/api/login/**","/static/**","/json/**","/authenticate").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        }

    }
}
