package com.example.CafeManagementSystem.repositories;

import com.example.CafeManagementSystem.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}


