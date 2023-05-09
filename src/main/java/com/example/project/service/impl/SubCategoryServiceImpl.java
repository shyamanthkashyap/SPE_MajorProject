package com.example.project.service.impl;

import com.example.project.entity.SubCategory;
import com.example.project.repository.SubCategoryRepository;
import com.example.project.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SubCategoryServiceImpl implements SubCategoryService {

    @Autowired
    private SubCategoryRepository subCategoryRepository;


    @Override
    public List<SubCategory> listSubCategory(Long id) {
        return subCategoryRepository.listSubCategory(id);
    }
}
