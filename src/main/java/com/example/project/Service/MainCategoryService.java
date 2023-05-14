package com.example.project.Service;

import com.example.project.Entity.MainCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MainCategoryService {

    List<MainCategory> listAllMainCategory();
}
