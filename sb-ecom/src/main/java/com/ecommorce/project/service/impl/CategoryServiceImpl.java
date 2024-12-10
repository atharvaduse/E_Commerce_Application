package com.ecommorce.project.service.impl;

import com.ecommorce.project.model.Category;
import com.ecommorce.project.repositories.CategoryRepository;
import com.ecommorce.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    //private List<Category> categories = new ArrayList<>();

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {

        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException (HttpStatus.NOT_FOUND,"Resurce  not found"));


        if(category == null) {
            return "Category not found";
        }
       categoryRepository.delete(category);


        return "Category with caegoryId:" + categoryId + " deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category savedCategory= categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource  not found"));

        category.setCategoryId(savedCategory.getCategoryId());

       savedCategory = categoryRepository.save(category);

       return savedCategory;
    }
}