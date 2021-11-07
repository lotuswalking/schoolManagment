package com.example.schoolmanagement.jpa.system;

import com.example.schoolmanagement.jpa.system.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMembershipRepository extends JpaRepository<UserMembership,Long> {


}
