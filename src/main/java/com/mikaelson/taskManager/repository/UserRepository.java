package com.mikaelson.taskManager.repository;

import com.mikaelson.taskManager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserLogin(String userLogin);

    boolean existsByUserLogin(String userLogin);
}
