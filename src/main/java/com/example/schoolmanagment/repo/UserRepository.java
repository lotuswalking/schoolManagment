package com.example.schoolmanagment.repo;

import com.example.schoolmanagment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User,Long> {
//    User findByUserId(Long userId);
    User findByUsername(String username);

}
