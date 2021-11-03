package com.example.schoolmanagement.Services;

import com.example.schoolmanagement.entity.Role;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repo.RoleRepository;
import com.example.schoolmanagement.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
@Service
public class UserService implements UserDetailsService {
    private Map<String, User> users;
    private Map<String, String> userNameByLowerCase;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public void addRole(String roleName) {
        if(roleRepository.findByRoleName(roleName)==null) {
            Role role = new Role(roleName);
            roleRepository.save(role);
        }
    }
    public void addUser(String username) {
        if(userRepository.findByUsername(username) ==null) {
            User user = new User();
            user.setUsername(username);
            user.setActiveFlag(true);
            user.setAuthorized(true);
            user.setEffectiveData(LocalDate.now());
            user.setExpiryDate(LocalDate.now().plusYears(1));

        }
    }

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