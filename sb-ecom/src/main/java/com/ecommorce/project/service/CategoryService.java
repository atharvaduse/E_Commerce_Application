package com.ecommorce.project.service;


import com.ecommorce.project.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    void createCategory(Category category);

    public String deleteCategory(Long categoryId);


    Category updateCategory(Category category, Long categoryId);
}
