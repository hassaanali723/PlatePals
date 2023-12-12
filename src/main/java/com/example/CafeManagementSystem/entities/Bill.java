package com.example.CafeManagementSystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bill {
    @Id
    @GeneratedValue
    private Long id;
    private String filename;
    private String contactNo;
    private String email;
    private String name;
    private String paymentMethod;

    @Column(columnDefinition = "json")
    private String productDetails;

    private Integer totalAmount;

    @Column(unique = true)
    private String uuid;

}

