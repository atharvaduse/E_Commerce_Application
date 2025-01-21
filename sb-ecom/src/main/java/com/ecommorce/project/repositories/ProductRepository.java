package com.ecommorce.project.repositories;

import com.ecommorce.project.model.Category;
import com.ecommorce.project.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

    Page<Product> findByproductNameLikeIgnoreCase(String keyword, Pageable pageDetails);
}
