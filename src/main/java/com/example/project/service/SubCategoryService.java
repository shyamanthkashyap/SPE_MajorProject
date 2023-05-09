package com.example.project.service;

import com.example.project.entity.SubCategory;
import com.example.project.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SubCategoryService {

    List<SubCategory> listSubCategory(Long id);
}
