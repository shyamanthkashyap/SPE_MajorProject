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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
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
    @PreAuthorize("hasRole('USER')")
    ResponseEntity<List<MainCategory>> getSubCategory(){
        return new ResponseEntity<>(HttpStatus.OK.value(), mainCategoryService.listAllMainCategory());
    }


}
