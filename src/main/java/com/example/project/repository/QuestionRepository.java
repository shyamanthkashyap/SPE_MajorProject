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

    @Query(value = "with t1 as (\n" +
            "    select question_id as id, match(q.title) against(?1 in natural language mode) as title_score\n" +
            "    from questions q\n" +
            "),\n" +
            "     t2 as (\n" +
            "         select question_id as id, match(q.body) against(?1 in natural language mode) as body_score\n" +
            "         from questions q\n" +
            "     ),\n" +
            "     t3 as (\n" +
            "         select q.question_id                                                           as id,\n" +
            "                match(a.answer_body) against(?1 in natural language mode) as answer_body_score\n" +
            "         from answers a\n" +
            "                  natural right outer join questions q\n" +
            "     ),\n" +
            "     t4 as (select t1.id, sum(title_score) + sum(body_score) + sum(answer_body_score) as score\n" +
            "            from t1\n" +
            "                     natural join t2\n" +
            "                     natural join t3\n" +
            "            group by id\n" +
            "            order by sum(title_score) + sum(body_score) + sum(answer_body_score) desc limit 8)\n" +
            "select *\n" +
            "from t4\n" +
            "         join questions on t4.id = questions.question_id order by score desc ;", nativeQuery=true)
    List<Questions> findRelatedQuestions(String text);
}
