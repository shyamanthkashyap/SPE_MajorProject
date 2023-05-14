package com.example.project.Service.Impl;

import com.example.project.Entity.SubCategory;
import com.example.project.Repository.SubCategoryRepository;
import com.example.project.Service.SubCategoryService;
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
