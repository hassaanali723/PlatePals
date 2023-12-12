package com.example.CafeManagementSystem.services;

import com.example.CafeManagementSystem.DTO.BillDTO;
import com.example.CafeManagementSystem.DTO.SuccessResponse;
import com.example.CafeManagementSystem.constants.CafeConstants;
import com.example.CafeManagementSystem.entities.Bill;
import com.example.CafeManagementSystem.repositories.BillRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BIllService {

    public final BillRepository billRepository;
    public SuccessResponse generateReport(Map<String, Object> billRequest){

        try{
            String filename;
            if(billRequest.containsKey("isGenerate") && !(Boolean) billRequest.get("isGenerate")){
                filename = (String) billRequest.get("uuid");
            }
            else{
                filename = getUuid() + ".pdf";
                billRequest.put("uuid", filename);
                insertBill(convertMapToBillDto(billRequest));
            }

            // create pdf document
             generatePdf(billRequest,filename);

            return new SuccessResponse("PDF created");

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new SuccessResponse("Error generating the bill.");
    }

    public void generatePdf(Map<String, Object> billRequest, String fileName) throws IOException, DocumentException {
        String name = (String) billRequest.get("name");
        String contactNo = (String) billRequest.get("contactNumber");
        String email = (String) billRequest.get("email");

        // Create a PDF document
        Document document = new Document();

        // Initialize PdfWriter and add content to the document
        PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + fileName));
        document.open();

        // Add content to the PDF document based on the billRequest data
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk nameChunk = new Chunk("Name: " + name, font);
        Chunk contactNoChunk = new Chunk("Contact No: " + contactNo, font);
        Chunk emailChunk = new Chunk("Email: " + email, font);

        document.add(nameChunk);
        document.add(contactNoChunk);
        document.add(emailChunk);

        // Add more details as needed

        // Close the document
        document.close();
    }




    private String getUuid() {
        return "BILL-" + Instant.now().toEpochMilli();
    }

    private void insertBill(BillDTO billRequest) {

        Bill bill = new Bill();
        bill.setUuid(billRequest.getUuid());
        bill.setEmail(billRequest.getEmail());
        bill.setName(billRequest.getName());
        bill.setContactNo(billRequest.getContactNo());
        bill.setPaymentMethod(billRequest.getPaymentMethod());
        bill.setProductDetails(billRequest.getProductDetails());
        bill.setTotalAmount(billRequest.getTotalAmount());

        billRepository.save(bill);
    }

    private BillDTO convertMapToBillDto(Map<String, Object> billRequest) {
        // Extract fields from the map

        String fileanme = (String) billRequest.get("fileName");
        String name = (String) billRequest.get("name");
        String contactNo = (String) billRequest.get("contactNumber");
        String email = (String) billRequest.get("email");
        String paymentMethod = (String) billRequest.get("paymentMethod");
        String productDetails = (String) billRequest.get("productDetails");
        // Assuming totalAmount is an Integer, adjust the type accordingly
        Integer totalAmount = Integer.parseInt((String) billRequest.get("totalAmount"));

        // Generate UUID for a new bill
        String uuid = getUuid();

        // Create a new BillDTO
        return new BillDTO(fileanme, contactNo, email,name, paymentMethod,productDetails, totalAmount,uuid);
    }

}
