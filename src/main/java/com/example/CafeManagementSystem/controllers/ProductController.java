package com.example.CafeManagementSystem.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    @GetMapping
    public String getProduct(){
        return "Goodbye to  10pearls";
    };
};
