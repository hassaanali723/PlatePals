package com.example.CafeManagementSystem.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillDTO {
    private String filename;
    private String contactNo;
    private String email;
    private String name;
    private String paymentMethod;
    private String productDetails;
    private Integer totalAmount;
    private String uuid;

}