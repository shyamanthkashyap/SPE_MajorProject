package com.example.project.controller;

import com.example.project.entity.Location;
import com.example.project.entity.MainCategory;
import com.example.project.entity.SubCategory;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.service.LocationService;
import com.example.project.service.MainCategoryService;
import com.example.project.service.SubCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    private MainCategoryService mainCategoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping("/list/{id}")
    ResponseEntity<List<SubCategory>> getSubCategory(@PathVariable Long id) throws Exception {
        try {
            logger.info("Executing getSubCategory Endpoint",
                    "method", "GET",
                    "path", "/list/{id}",
                    "status", HttpStatus.OK.value()
            );
            return new ResponseEntity<>(HttpStatus.OK.value(), subCategoryService.listSubCategory((id)));
        }
        catch (Exception e){
            logger.error("Error Executing getSubCategory","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }

    @GetMapping("/listAll")
    ResponseEntity<List<MainCategory>> getMainCategory() throws Exception {
        try {
            logger.info("Executing getMainCategory Endpoint",
                    "method", "GET",
                    "path", "/listAll",
                    "status", HttpStatus.OK.value()
            );
            return new ResponseEntity<>(HttpStatus.OK.value(), mainCategoryService.listAllMainCategory());
        }
        catch (Exception e){
            logger.error("Error Executing getMainCategory","status","ERROR","Message",e.getMessage(),"Stacktrace",e.getStackTrace());
            throw new Exception();
        }
    }


}
