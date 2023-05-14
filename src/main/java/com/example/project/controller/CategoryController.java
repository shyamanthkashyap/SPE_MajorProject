package com.example.project.controller;

import com.example.project.entity.Location;
import com.example.project.entity.MainCategory;
import com.example.project.entity.SubCategory;
import com.example.project.entity.response.ResponseEntity;
import com.example.project.service.LocationService;
import com.example.project.service.MainCategoryService;
import com.example.project.service.SubCategoryService;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
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

    private static final Logger logger = LogManager.getLogger(CategoryController.class);
    @Autowired
    private MainCategoryService mainCategoryService;

    @Autowired
    private SubCategoryService subCategoryService;

    @GetMapping("/list/{id}")
    ResponseEntity<List<SubCategory>> getSubCategory(@PathVariable Long id) throws Exception {
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing getSubCategory Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/list/{id}],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            return new ResponseEntity<>(HttpStatus.OK.value(), subCategoryService.listSubCategory((id)));
        }
        catch (Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing getSubCategory\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/list/{id}],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }

    @GetMapping("/listAll")
    ResponseEntity<List<MainCategory>> getMainCategory() throws Exception {
        StringBuilder reqMessage = new StringBuilder();
        reqMessage.append("Message = \"Executing getMainCategory Endpoint\",");
        reqMessage.append("method = [GET],");
        reqMessage.append("path = [/listAll],");
        reqMessage.append("status = "+HttpStatus.OK.value());
        try {
            logger.info(reqMessage);
            return new ResponseEntity<>(HttpStatus.OK.value(), mainCategoryService.listAllMainCategory());
        }
        catch (Exception e){
            StringBuilder errMessage = new StringBuilder();
            errMessage.append("Message = \"Error Executing getMainCategory\",");
            errMessage.append("method = [GET],");
            errMessage.append("path = [/listAll],");
            errMessage.append("status = "+500);
            errMessage.append(",ExceptionMessage = "+e.getMessage());
            logger.error(errMessage);
            throw new Exception();
        }
    }


}
