package com.ecommorce.project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Categories")
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;


    @Column
    @NotBlank(message = "CATEGORY NOT BLANK")
    @Size(min = 5, message = "Category name must contain at least 5 characters")
    private String CategoryName;

    public void setId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
