package com.example.CafeManagementSystem.controllers;

import com.example.CafeManagementSystem.DTO.CategoryDTO;
import com.example.CafeManagementSystem.DTO.SuccessResponse;
import com.example.CafeManagementSystem.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/item-categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDTO> getAllItemCategories() {
        return categoryService.getAllItemCategories();
    }

    @PostMapping
    public SuccessResponse createItemCategory(@RequestBody CategoryDTO categoryDTO){
        return categoryService.createItemCategory(categoryDTO);
    }

    @PutMapping("/{id}")
    public SuccessResponse updateItemCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO){
        return categoryService.updateItemCategory(id, categoryDTO);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse deleteItemCategory(@PathVariable Long id){
        return categoryService.deleteItemCategory(id);
    }
}
