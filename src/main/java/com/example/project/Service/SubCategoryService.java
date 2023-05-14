package com.example.project.Service;

import com.example.project.Entity.SubCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubCategoryService {

    List<SubCategory> listSubCategory(Long id);
}
