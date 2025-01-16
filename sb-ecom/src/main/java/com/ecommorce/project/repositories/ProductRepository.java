package com.ecommorce.project.repositories;

import com.ecommorce.project.model.Category;
import com.ecommorce.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryOrderByPriceAsc(Category category);
    List<Product> findByproductNameLikeIgnoreCase(String keyword);
}
