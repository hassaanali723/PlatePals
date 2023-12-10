package com.example.CafeManagementSystem.repositories;

import com.example.CafeManagementSystem.entities.Category;
import com.example.CafeManagementSystem.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);
}
