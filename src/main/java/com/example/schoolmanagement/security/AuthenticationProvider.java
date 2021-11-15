package com.example.schoolmanagement.security;

import com.example.schoolmanagement.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    protected UserService userService;

    @Override  //如果没有里面的确认,任何密码都成为可接受的密码
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        if(!userService.isValidCredentials(userDetails.getUsername(),usernamePasswordAuthenticationToken.getCredentials().toString())) {
            throw new BadCredentialsException("User authentication faild, check logs for more information");
        }
    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        return userService.loadUserByUsername(s);
    }
}
