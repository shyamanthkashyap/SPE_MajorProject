package com.example.project.repository;

import com.example.project.entity.User;
import com.example.project.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsername(String username);

    Boolean existsUserByUsername(String username);

    Boolean existsUsersByEmail(String email);

    User findUserByUserId (Integer id);
}