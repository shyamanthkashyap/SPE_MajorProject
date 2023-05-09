package com.example.project.repository;

import com.example.project.entity.Questions;
import com.example.project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Questions, Long> {

    @Query(value = "select *\n" +
            "from questions q\n" +
            "         join sub_category on q.sub_category_id = sub_category.sub_category_id\n" +
            "         join main_category mc on mc.category_id = sub_category.category_id\n" +
            "where mc.category_id = ?1 order by post_time desc", nativeQuery = true)
    List<Questions> listCatgoryQuestions(Long ctgyId);

    @Query(value="select *\n" +
            "from questions q\n" +
            "where q.sub_category_id = ?1 order by post_time desc",nativeQuery = true)
    List<Questions> findAllBySubCategoryId(Long ctgyId);

    List<Questions> findAllByUserOrderByPostTimeDesc(User user);
}
