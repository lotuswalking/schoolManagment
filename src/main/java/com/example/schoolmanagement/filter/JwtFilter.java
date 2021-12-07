package com.example.schoolmanagement.filter;

import com.example.schoolmanagement.Services.UserService;
import com.example.schoolmanagement.utility.JWTUtility;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
@Log
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtility jwtUtility;
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        String token = null;
        String userName = null;
        String referAddr = request.getHeader("Referer");
        String requestMethod = request.getMethod();
        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for( Cookie cookie : cookies)  {
//                log.info("Cookie name: "+ cookie.getName() +" cookie valueL " + cookie.getValue());
//                if("password".equalsIgnoreCase(cookie.getName())) {
//                    String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//                    if(username != null && !username.equals("anonymousUser")) {
//                        UserDetails userDetails
//                                = userService.loadUserByUsername(username);
//                        String jwtToken = jwtUtility.generateToken(userDetails);
//                        response.setHeader("Authorization",jwtToken);
//                    }
//
//                }
//            }
//        }


        if(requestMethod.equalsIgnoreCase("post") && referAddr.endsWith("/login")) {
            String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            UserDetails userDetails
                    = userService.loadUserByUsername(username);
            String jwtToken = jwtUtility.generateToken(userDetails);
            response.setHeader("Authorization",jwtToken);

        }


        if(null != authorization && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            userName = jwtUtility.getUsernameFromToken(token);
        }
        if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails
                    = userService.loadUserByUsername(userName);

                if(jwtUtility.validateToken(token,userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(request, response);
    }
}
