package com.example.schoolmanagment.Services;

import com.example.schoolmanagment.entity.User;
import com.example.schoolmanagment.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
@Service
public class UserService implements UserDetailsService {
    private Map<String, User> users;
    private Map<String, String> userNameByLowerCase;
    @Autowired
    private UserRepository userRepository;
    public  void populateUsers() {
        Map<String, User> userList = new HashMap<>();
        userRepository.findAll()
                .forEach(user -> userList.putAll(addToMap(user)));
        users = userList;
        userNameByLowerCase = new HashMap<>();
        for(String userName : users.keySet()) {
            userNameByLowerCase.put(userName.toLowerCase(),userName);
        }

    }

    private Map<String, User> addToMap(User user) {
        Map<String,User> userMap = new HashMap<>();
        user.setAuthorized(true);
        userMap.put(user.getUsername(),user);
        return userMap;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = null;
        s = s.toLowerCase();
        user = userRepository.findByUsername(s);
        if(user==null) {
            throw  new UsernameNotFoundException("Could not find user.");
        }
        return user;
//        if(users.containsKey(s)) {
//            user = users.get(s);
//        }else {
//            throw  new UsernameNotFoundException("Could not find user.");
//        }
//        return user;
    }
    public boolean isValidCredentials(String username, String password) {
        boolean correct = false;
        UserDetails user = loadUserByUsername(username);
        if(user.getPassword().equals(password)) {
            correct = true;
        }
        return correct;
    }
}
