package com.example.project.service.impl;

import com.example.project.entity.MainCategory;
import com.example.project.repository.MainCategoryRepository;
import com.example.project.service.MainCategoryService;
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
