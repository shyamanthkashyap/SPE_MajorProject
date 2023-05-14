package com.example.project.Service.Impl;

import com.example.project.Entity.MainCategory;
import com.example.project.Repository.MainCategoryRepository;
import com.example.project.Service.MainCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainCategoryServiceImpl implements MainCategoryService {

    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    @Override
    public List<MainCategory> listAllMainCategory() {
        return mainCategoryRepository.findAll();
    }
}
