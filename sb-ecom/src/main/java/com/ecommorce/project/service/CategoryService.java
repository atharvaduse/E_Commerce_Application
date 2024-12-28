package com.ecommorce.project.service;


import com.ecommorce.project.payload.CategoryDTO;
import com.ecommorce.project.payload.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    public CategoryDTO deleteCategory(Long categoryId);


    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
