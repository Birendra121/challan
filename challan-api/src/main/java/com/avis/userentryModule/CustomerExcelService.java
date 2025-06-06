package com.avis.userentryModule;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avis.entity.User;

import java.io.*;

@Service
public class CustomerExcelService {

	@Autowired
	private CustomerRepository customerRepository;
	
    private static final String FILE_PATH = "customers.xlsx";

    
    public void saveCustomer(Customer customer) {
        // 1. Save to DB
        customerRepository.save(customer);

        // 2. Write to Excel
        writeToExcel(customer);
    }
    
    public void writeToExcel(Customer customer) {
        File file = new File(FILE_PATH);
        Workbook workbook;
        Sheet sheet;

        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
                fis.close();
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Customers Details");

                // Create Header Row
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Name");
                header.createCell(1).setCellValue("Email");
                header.createCell(2).setCellValue("Mobile Number");
            }

            // Append new Customer data
            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(rowCount + 1);
            row.createCell(0).setCellValue(customer.getCustomerName());
            row.createCell(1).setCellValue(customer.getEmail());
            row.createCell(2).setCellValue(customer.getMobileNumber());

            try (FileOutputStream fos = new FileOutputStream(FILE_PATH)){
            	 workbook.write(fos);
            } catch (IOException ioException) {
                System.err.println("Could not write to Excel file. It might be open in another program.");
                ioException.printStackTrace();
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

