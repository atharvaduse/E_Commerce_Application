package com.ecommorce.project.controller;

import com.ecommorce.project.model.Product;
import com.ecommorce.project.payload.ProductDTO;
import com.ecommorce.project.payload.ProductResponse;
import com.ecommorce.project.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody Product product, @PathVariable Long categoryId){

       ProductDTO productDTO =  productService.addProduct(categoryId,product);

        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(@RequestBody ProductDTO productDTO){

        //Product products = modelMapper.map(productDTO,Product.class);

        ProductResponse productResponse = productService.getAllProducts();

        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){
        ProductResponse productResponse = productService.searchByCategory(categoryId);

        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword){

        ProductResponse productResponse = productService.searchProductByKeyword(keyword);

        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
}
