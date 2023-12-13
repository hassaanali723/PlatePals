package com.example.CafeManagementSystem.services;

import com.example.CafeManagementSystem.DTO.BillDTO;
import com.example.CafeManagementSystem.DTO.SuccessResponse;
import com.example.CafeManagementSystem.constants.CafeConstants;
import com.example.CafeManagementSystem.entities.Bill;
import com.example.CafeManagementSystem.repositories.BillRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.*;

import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

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
        String paymentMethod = (String) billRequest.get("paymentMethod");
        String productDetails = (String) billRequest.get("productDetails");
        String totalAmount = (String) billRequest.get("totalAmount");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> products = objectMapper.readValue(productDetails, List.class);

        // Create a PDF document
        Document document = new Document();

        // Initialize PdfWriter and add content to the document
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + fileName));
        document.open();

        writer.setBoxSize("art", new com.itextpdf.text.Rectangle(36, 54, 559, 788));
        // Add content to the PDF document based on the billRequest data

        setRectangleInPdf(document);

        // Add borders for the entire page
        document.add(new Rectangle(36, 36, 559, 806)); // Change the margins as needed

        Paragraph headerchunk = new Paragraph("PlatePals", getFont("header"));
        headerchunk.setAlignment(Element.ALIGN_CENTER);

        Chunk nameChunk = new Chunk("Name: " + name, getFont("data"));
        Chunk contactNoChunk = new Chunk("Contact No: " + contactNo,  getFont("data"));
        Chunk emailChunk = new Chunk("Email: " + email,  getFont("data"));
        Chunk paymentMethodChunk = new Chunk("Payment Method: " + paymentMethod,  getFont("data"));

        document.add(headerchunk);
        document.add(nameChunk);
        document.add(createNewline());
        document.add(contactNoChunk);
        document.add(createNewline());
        document.add(emailChunk);
        document.add(createNewline());
        document.add(paymentMethodChunk);

        // Add a newline
        document.add(createNewline());

        // Table of lists of products
        document.add(createProductTable(products));

        // Add a newline
        document.add(createNewline());

        // Total Bill
        document.add(createParagraph("Total Bill: "+ totalAmount, getFont("data"))); // Replace this with your actual total amount

        // Thank you message
        document.add(createParagraph("Thank you for your purchase!",  getFont("data")));

        // Close the document
        document.close();
    }

    private void setRectangleInPdf(Document document) throws DocumentException{
        Rectangle rect = new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);

        rect.setBorderColor(BaseColor.BLACK);

        rect.setBorderWidth(1);
        document.add(rect);
    }

    public static Font getFont(String type) {
        Font font = null;

        switch (type.toLowerCase()) {
            case "header":
                font = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD);
                break;
            case "data":
                font = FontFactory.getFont(FontFactory.TIMES, 12);
                break;
            // Add more cases as needed

            default:
                // Default font if the type is not recognized
                font = FontFactory.getFont(FontFactory.HELVETICA, 12);
                break;
        }
        return font;
    }

    private static Paragraph createParagraph(String content, Font font) {
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        return paragraph;
    }

    private static Paragraph createNewline() {
        return new Paragraph(" ");
    }

    private static PdfPTable createProductTable(List<Map<String, Object>> products) {

        PdfPTable table = new PdfPTable(5); // 5 columns for product name, category, quantity, price, sub total

        // Set table header properties
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        BaseColor headerBackgroundColor = new BaseColor(255, 255, 0); // Yellow color
        int headerFontSize = 12;

        // Add table headers with bold and yellow background
        PdfPCell cell = new PdfPCell(new Phrase("Product Name", headerFont));
        cell.setBackgroundColor(headerBackgroundColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Category", headerFont));
        cell.setBackgroundColor(headerBackgroundColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Quantity", headerFont));
        cell.setBackgroundColor(headerBackgroundColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Price", headerFont));
        cell.setBackgroundColor(headerBackgroundColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Sub Total", headerFont));
        cell.setBackgroundColor(headerBackgroundColor);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5);
        table.addCell(cell);

        // Add table headers
        table.addCell("Product Name");
        table.addCell("Category");
        table.addCell("Quantity");
        table.addCell("Price");
        table.addCell("Sub Total");

        // Add products to the table
        for (Map<String, Object> product : products) {
            table.addCell((String) product.get("name"));
            table.addCell((String) product.get("category"));
            table.addCell(String.valueOf(product.get("quantity")));
            table.addCell(String.valueOf(product.get("price")));
            table.addCell(String.valueOf(product.get("total")));
        }

        return table;
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
