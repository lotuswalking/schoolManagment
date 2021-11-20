package com.example.schoolmanagement.Services;

import com.example.schoolmanagement.jpa.school.StudentRepository;
import com.example.schoolmanagement.jpa.school.TeacherRepository;
import com.example.schoolmanagement.jpa.school.entity.Student;
import com.example.schoolmanagement.jpa.school.entity.Teacher;
import com.example.schoolmanagement.jpa.system.*;
import com.example.schoolmanagement.jpa.system.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class MyService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;


    private final GroupRepository groupRepository;


    private final UserMembershipRepository userMembershipRepository;


    private final GroupPrivilegeRepository groupPrivilegeRepository;


    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;



    public void addRole(Long id,String roleName) {
        if (!roleRepository.existsById(id)) {
            Role role = new Role(roleName);
            role.setRoleId(id);
            roleRepository.save(role);
        }
    }

    public void  addStudents(String firstName,String lastName, String email) {
        if(studentRepository.findByEmail(email) == null) {
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(email);
            studentRepository.save(student);
        }
    }
    public void addStudent(Student student) {
        if(!studentRepository.existsById(student.getId())) {
        if (!studentRepository.existsById(student.getId())) {
            studentRepository.save(student);
        }
    }
    public void addTeacher(Teacher teacher) {
        if(!teacherRepository.existsById(teacher.getId())) {
            teacherRepository.save(teacher);
        }
    }

    public void addUser(Long id,String username) {
        if (!userRepository.existsById(id)) {
            User user = new User();
            user.setUserId(id);
            user.setUsername(username);
            user.setActiveFlag(true);
            user.setAuthorized(true);
            user.setEffectiveData(LocalDate.now());
            user.setExpiryDate(LocalDate.now().plusYears(1));
            userRepository.save(user);

        }
    }
    public void mapGroupToRole(Long id, String groupName,String roleName) {

        if (!groupPrivilegeRepository.existsById(id) ) {
            Group group = groupRepository.findByGroupName(groupName);
            Role role = roleRepository.findByRoleName(roleName);
            GroupPrivilege groupPrivilege = new GroupPrivilege();
            groupPrivilege.setGroupPrivilegeId(id);
            groupPrivilege.setRole(role);
            groupPrivilege.setActiveFlag(true);
            groupPrivilege.setEffectiveDate(LocalDate.now());
            groupPrivilege.setExpiryDate(LocalDate.now().plusMonths(6));
            groupPrivilege.setGroup(group);
            groupPrivilegeRepository.save(groupPrivilege);
        }

    }
    public void mapUserToGroup(Long id,String username, String groupName) {
        if (!userMembershipRepository.existsById(id) ) {
            Group group = groupRepository.findByGroupName(groupName);
            User user = userRepository.findByUsername(username);
            UserMembership userMembership = new UserMembership();
            userMembership.setUserMembershipId(id);
            userMembership.setUser(user);
            userMembership.setGroup(group);
            userMembership.setActiveFlag(true);
            userMembership.setEffectiveDate(LocalDate.now());
            userMembership.setExpiryDate(LocalDate.now().plusMonths(6));
            userMembershipRepository.save(userMembership);
        }
    }

    public void addGroup(Long id,String groupName) {
        if (!groupRepository.existsById(id) ) {
            Group group = new Group();
            group.setGroupId(id);
            group.setGroupName(groupName);
            groupRepository.save(group);
        }
    }
    public void addTeacher(Long id, String firstName, String lastName, String email) {
        if(!teacherRepository.existsById(id)) {
            Teacher teacher = new Teacher();
//            teacher.setId(id);
            teacher.setFirstName(firstName);
            teacher.setEmail(email);
            teacher.setLastName(lastName);
            teacherRepository.save(teacher);
        }
    }
    }

