package com.example.project.controller;

import com.example.project.entity.Location;
import com.example.project.entity.MainCategory;
import com.example.project.entity.SubCategory;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.service.LocationService;
import com.example.project.service.MainCategoryService;
import com.example.project.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private MainCategoryService mainCategoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping("/list/{id}")
    ResponseEntity<List<SubCategory>> getMainCategory(@PathVariable Long id){
        return new ResponseEntity<>(HttpStatus.OK.value(), subCategoryService.listSubCategory((id)));
    }

    @GetMapping("/listAll")
    ResponseEntity<List<MainCategory>> getSubCategory(){
        return new ResponseEntity<>(HttpStatus.OK.value(), mainCategoryService.listAllMainCategory());
    }


}
