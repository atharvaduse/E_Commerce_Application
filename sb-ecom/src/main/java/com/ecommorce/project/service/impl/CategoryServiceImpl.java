package com.ecommorce.project.service.impl;

import com.ecommorce.project.exceptions.APIException;
import com.ecommorce.project.exceptions.ResourceNotFoundException;
import com.ecommorce.project.model.Category;
import com.ecommorce.project.payload.CategoryDTO;
import com.ecommorce.project.payload.CategoryResponse;
import com.ecommorce.project.repositories.CategoryRepository;
import com.ecommorce.project.service.CategoryService;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories() {

        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()){
            throw new APIException("No category created till now");
        }
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);


        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

        Category category = modelMapper.map(categoryDTO,Category.class);

        Category CategoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());
        if (CategoryFromDb != null){
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists !!!");
        }

        Category savedCategory =  categoryRepository.save(category);

        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);

        return savedCategoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

       categoryRepository.delete(category);

       return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {

        Category category = modelMapper.map(categoryDTO,Category.class);

        Category savedCategory= categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));

        category.setCategoryId(savedCategory.getCategoryId());

       savedCategory = categoryRepository.save(category);

       CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory,CategoryDTO.class);

       return savedCategoryDTO;
    }
}
