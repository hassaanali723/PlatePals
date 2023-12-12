package com.example.CafeManagementSystem.repositories;

import com.example.CafeManagementSystem.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
