package com.example.schoolmanagement.jpa.system;

import com.example.schoolmanagement.jpa.system.entity.GroupPrivilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupPrivilegeRepository extends JpaRepository<GroupPrivilege, Long> {
}
