package com.example.CafeManagementSystem.services;

import com.example.CafeManagementSystem.DTO.CategoryDTO;
import com.example.CafeManagementSystem.DTO.SuccessResponse;
import com.example.CafeManagementSystem.entities.Category;
import com.example.CafeManagementSystem.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    public final CategoryRepository categoryRepository;
    public List<CategoryDTO> getAllItemCategories(){
        return categoryRepository
                .findAll()
                .stream()
                .map(ItemCat -> new CategoryDTO(ItemCat.getId(), ItemCat.getName()))
                .toList();
    };

    public SuccessResponse createItemCategory(CategoryDTO categoryDTO){
        Category category = new Category(categoryDTO.getName());
        categoryRepository.save(category);
        return new SuccessResponse("Category of Item Created Successfully");
    }

}

