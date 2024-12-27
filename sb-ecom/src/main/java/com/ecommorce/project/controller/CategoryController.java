package com.ecommorce.project.controller;

import com.ecommorce.project.model.Category;
import com.ecommorce.project.payload.CategoryDTO;
import com.ecommorce.project.payload.CategoryResponse;
import com.ecommorce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CategoryController {

@Autowired
private CategoryService categoryService;

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories() {
   CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse,HttpStatus.OK);
    }


    @PostMapping("/api/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid   @RequestBody CategoryDTO categoryDTO){

      CategoryDTO savedCategoryDTO =categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO,HttpStatus.OK) ;
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){

    String status = categoryService.deleteCategory(categoryId);
    return new ResponseEntity<>(status, HttpStatus.OK);

 }


    @PutMapping("/api/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO,@PathVariable Long categoryId){


        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO,categoryId);
            return new ResponseEntity<CategoryDTO>(savedCategoryDTO, HttpStatus.OK);


    }

}
