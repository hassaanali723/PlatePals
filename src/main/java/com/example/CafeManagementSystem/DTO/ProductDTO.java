package com.example.CafeManagementSystem.DTO;

import com.example.CafeManagementSystem.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProductDTO {

    private Long id = 1L;
    private String name;
    private CategoryDTO category;
    private String description;
    private Integer price;
    private String status;

}
