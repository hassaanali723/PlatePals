package com.example.CafeManagementSystem.controllers;

import com.example.CafeManagementSystem.DTO.ProductDTO;
import com.example.CafeManagementSystem.DTO.SuccessResponse;
import com.example.CafeManagementSystem.entities.Product;
import com.example.CafeManagementSystem.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProduct(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id){
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){
        return productService.createProduct(productDTO);
    }

    @PutMapping("/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/getByCategory/{categoryId}")
    public List<ProductDTO> getProductsByCategoryId(@PathVariable Long categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @PatchMapping("/change-status/{id}")
    public ProductDTO changeProductStatus(@PathVariable Long id, @RequestBody Map<String, String> statusMap) {
        String newStatus = statusMap.get("status");
        return productService.changeProductStatus(id, newStatus);
    }



//    @PostMapping(path = "/add")
//    public SuccessResponse addNewProduct(@RequestMapping ProductDTO productDTO){
//
//    }
};
