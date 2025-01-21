package com.ecommorce.project.service.impl;

import com.ecommorce.project.exceptions.APIException;
import com.ecommorce.project.exceptions.ResourceNotFoundException;
import com.ecommorce.project.model.Category;
import com.ecommorce.project.model.Product;
import com.ecommorce.project.payload.ProductDTO;
import com.ecommorce.project.payload.ProductResponse;
import com.ecommorce.project.repositories.CategoryRepository;
import com.ecommorce.project.repositories.ProductRepository;
import com.ecommorce.project.service.FileService;
import com.ecommorce.project.service.ProductService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private  FileService fileService;

    @Value("${project.image}")
    private String path;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category","categoryId",categoryId));
        // check if product already present or not
        boolean isProductPresent = true;
        List<Product> products = category.getProducts();
        for (Product value:products) {
            if (value.getProductName().equals(productDTO.getProductName())) {
                isProductPresent = false;
                break;
            }
        }
        if(isProductPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() -
                    ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);

            return modelMapper.map(savedProduct, ProductDTO.class);
        }
        else {
            throw new APIException("Product Already Exists!!");
        }
    }

    @Override
    public ProductResponse getAllProducts() {
        // products size is 0 or not

        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        if (products.isEmpty()){
            throw new APIException("No products exist");
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        // products size is 0 or not
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        // products size is 0 or not
        List<Product> products = productRepository.findByproductNameLikeIgnoreCase("%"+keyword+"%");
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse() ;
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        Product product = modelMapper.map(productDTO,Product.class);
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setPrice(product.getPrice());
        double specialPrice = product.getPrice() -
                ((product.getDiscount() * 0.01) * product.getPrice());
        productFromDb.setSpecialPrice(specialPrice);
        Product savedProduct = productRepository.save(productFromDb);
        ProductDTO updatedProductDTO = modelMapper.map(savedProduct,ProductDTO.class);

        return updatedProductDTO;
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {

        // check if that productd is present in our records or not

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","productId",productId));

        productRepository.delete(product);

        return modelMapper.map(product,ProductDTO.class);

    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        // Get the product from DB
        Product productFromDb = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product","prodcutId",productId));
        //upload image to server
        // Get the file name of uploaded image
        String fileName = fileService.uploadImage(path, image);
        // updating the new file name to the product
        productFromDb.setImage(fileName);
        // Save updated product
        Product updatedProduct = productRepository.save(productFromDb);
        // return DTO after mapping product DTO
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }




}
