package com.example.schoolmanagement.repo;

import com.example.schoolmanagement.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMembershipRepository extends JpaRepository<UserMembership,Long> {


}
