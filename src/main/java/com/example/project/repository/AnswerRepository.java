package com.example.project.repository;

import com.example.project.entity.Answers;
import com.example.project.entity.Questions;
import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answers, Long> {
    List<Answers> queryByQuestionsQuestionId(Long questionId);

    List<Answers> findAllByUserOrderByPostTimeDesc(User user);

    @Query(value = "select count(*) from thumbs\n" +
            "where answer_id = ?1", nativeQuery = true)
    Long getLikes(Long answerId);
}
