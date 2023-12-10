package com.example.CafeManagementSystem.services;

import com.example.CafeManagementSystem.DTO.CategoryDTO;
import com.example.CafeManagementSystem.DTO.ProductDTO;
import com.example.CafeManagementSystem.DTO.SuccessResponse;
import com.example.CafeManagementSystem.entities.Category;
import com.example.CafeManagementSystem.entities.Product;
import com.example.CafeManagementSystem.repositories.CategoryRepository;
import com.example.CafeManagementSystem.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ProductDTO> getAllProducts(){
           return productRepository.findAll()
                   .stream()
                   .map(product -> new ProductDTO(
                           product.getId(),
                           product.getName(),
                           new CategoryDTO(product.getCategory().getId(),product.getCategory().getName()),
                           product.getDescription(),
                           product.getPrice(),
                           product.getStatus()
                   ))
                   .toList();

    }

    public ProductDTO getProductById(Long id){
        return productRepository.findById(id)
                .map(product -> new ProductDTO(
                    product.getId(),
                    product.getName(),
                    new CategoryDTO(product.getCategory().getId(),product.getCategory().getName()),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStatus()
                ))
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public ProductDTO createProduct(ProductDTO productDTO){
        Category category = categoryRepository.findById(productDTO.getCategory().getId())
                .orElseThrow(()-> new RuntimeException("Category not found with id: " + productDTO.getCategory().getId()));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(category);
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStatus(productDTO.getStatus());

        Product savedProduct = productRepository.save(product);
        return new ProductDTO(
                savedProduct.getId(),
                savedProduct.getName(),
                new CategoryDTO(savedProduct.getCategory().getId(), savedProduct.getCategory().getName()),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getStatus()
        );
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));


        Category category = categoryRepository.findById(productDTO.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + productDTO.getCategory().getId()));

        existingProduct.setName(productDTO.getName());
        existingProduct.setCategory(category);
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStatus(productDTO.getStatus());

        Product updatedProduct = productRepository.save(existingProduct);

        return new ProductDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                new CategoryDTO(updatedProduct.getCategory().getId(),updatedProduct.getCategory().getName()),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getStatus()
        );

    }

    public SuccessResponse deleteProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        productRepository.delete(existingProduct);
        return new SuccessResponse("Product Deleted Successfully");
    }

    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        List<Product> products = productRepository.findByCategory(category);

        return products.stream()
                .map(product -> new ProductDTO(
                        product.getId(),
                        product.getName(),
                        new CategoryDTO(product.getCategory().getId(),product.getCategory().getName()),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStatus()
                ))
                .toList();
    }

    public ProductDTO changeProductStatus(Long id, String newStatus) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

        existingProduct.setStatus(newStatus);

        Product updatedProduct = productRepository.save(existingProduct);

        return new ProductDTO(
                updatedProduct.getId(),
                updatedProduct.getName(),
                new CategoryDTO(updatedProduct.getCategory().getId(),updatedProduct.getCategory().getName()),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getStatus()
        );
    }



}
