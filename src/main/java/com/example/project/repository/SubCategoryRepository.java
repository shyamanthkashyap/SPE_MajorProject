package com.example.project.repository;

import com.example.project.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query(value = "select * from sub_category where category_id = ?1", nativeQuery = true)
    List<SubCategory> listSubCategory(Long id);

}
