package com.example.project.repository;

import com.example.project.entity.Answers;
import com.example.project.entity.Thumbs;
import com.example.project.entity.User;
import com.example.project.service.ThumbService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThumbRepository extends JpaRepository<Thumbs, Thumbs> {
    Optional<Thumbs> getThumbsByAnswersAndUser(Answers answers, User user);

}
