package com.ecommorce.project.service;

import com.ecommorce.project.model.Product;
import com.ecommorce.project.payload.ProductDTO;
import com.ecommorce.project.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);

    ProductResponse getAllProducts();

    ProductResponse searchByCategory(Long categoryId);

    //ProductResponse searchByKeyword(String keyword);

    ProductResponse searchProductByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, Product product);
}
