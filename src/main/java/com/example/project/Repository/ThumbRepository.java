package com.example.project.Repository;

import com.example.project.Entity.Answers;
import com.example.project.Entity.Thumbs;
import com.example.project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThumbRepository extends JpaRepository<Thumbs, Thumbs> {
    Optional<Thumbs> getThumbsByAnswersAndUser(Answers answers, User user);

}
