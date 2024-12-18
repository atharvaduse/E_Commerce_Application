package com.ecommorce.project.repositories;

import com.ecommorce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    // to declare custom methods in repository we just need to declare the method and  its implementation will be handled by JPA repo
    // for this we have to follow some conventions with its entity class
    //e.g.  findByCategoryName(String categoryName)

    Category findByCategoryName(String categoryName);

}

