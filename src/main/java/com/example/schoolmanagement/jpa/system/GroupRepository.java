package com.example.schoolmanagement.jpa.system;

import com.example.schoolmanagement.jpa.system.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByGroupName(String groupname);
}
