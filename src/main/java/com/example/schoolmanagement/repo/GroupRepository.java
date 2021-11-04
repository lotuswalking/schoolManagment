package com.example.schoolmanagement.repo;

import com.example.schoolmanagement.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Group findByGroupName(String groupname);
}
