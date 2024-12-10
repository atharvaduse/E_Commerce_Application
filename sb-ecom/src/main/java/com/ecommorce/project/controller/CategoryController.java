package com.ecommorce.project.controller;

import com.ecommorce.project.model.Category;
import com.ecommorce.project.service.CategoryService;
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
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories,HttpStatus.OK);
    }


    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createCategory(@RequestBody Category category){

      categoryService.createCategory(category);
        return new ResponseEntity<>("Category added succesfully",HttpStatus.OK) ;
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
try {
    String status = categoryService.deleteCategory(categoryId);

    return new ResponseEntity<>(status, HttpStatus.OK);
}catch (ResponseStatusException e){
    return new ResponseEntity<>(e.getReason(),e.getStatusCode());
}
    }


    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category,@PathVariable Long categoryId){

        try {
            Category savedCategory = categoryService.updateCategory(category,categoryId);
            return new ResponseEntity<>("Category with category id: " + categoryId, HttpStatus.OK);
        }catch (ResponseStatusException e){

            return new ResponseEntity<>(e.getReason(),e.getStatusCode());
        }

    }

}