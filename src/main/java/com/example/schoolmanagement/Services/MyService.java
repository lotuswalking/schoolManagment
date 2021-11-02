package com.example.schoolmanagement.Services;

import com.example.schoolmanagement.entity.Role;
import com.example.schoolmanagement.entity.User;
import com.example.schoolmanagement.repo.GroupRepository;
import com.example.schoolmanagement.repo.RoleRepository;
import com.example.schoolmanagement.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MyService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private GroupRepository groupRepository;

    public void addRole(String roleName) {
        if (roleRepository.findByRoleName(roleName) == null) {
            Role role = new Role(roleName);
            roleRepository.save(role);
        }
    }

    public void addUser(String username) {
        if (userRepository.findByUsername(username) == null) {
            User user = new User();
            user.setUsername(username);
            user.setActiveFlag(true);
            user.setAuthorized(true);
            user.setEffectiveData(LocalDate.now());
            user.setExpiryDate(LocalDate.now().plusYears(1));
            userRepository.save(user);

        }
    }
    public void addGroup(String groupname) {
        if (groupRepository.findByGroupName(groupname) == null) {
            User user = new User();
            user.setUsername(groupname);
            user.setActiveFlag(true);
            user.setAuthorized(true);
            user.setEffectiveData(LocalDate.now());
            user.setExpiryDate(LocalDate.now().plusYears(1));

        }
    }
    public void addRoleToUser(String username, String rolename) {
        User user = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(rolename);
        user.getRoles().add(role);
        userRepository.save(user);
    }


}
