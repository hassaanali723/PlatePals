package com.example.CafeManagementSystem.controllers;

import com.example.CafeManagementSystem.DTO.SuccessResponse;
import com.example.CafeManagementSystem.constants.CafeConstants;
import com.example.CafeManagementSystem.entities.Bill;
import com.example.CafeManagementSystem.services.BIllService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bill")
public class BillController {

    private final BIllService billService;
    @PostMapping("/generateReport")
    public SuccessResponse generateBillReport(@RequestBody Map<String, Object> billRequest) {
        try {
            System.out.println("test");
            return billService.generateReport(billRequest);
        } catch (Exception e) {
            e.printStackTrace();
            return new SuccessResponse("Error generating the bill.");
        }
    }
}
