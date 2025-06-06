package com.avis.userentryModule;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class CustomerExportService {

	@Autowired
    private CustomerRepository customerRepository;

    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Customer> customers = customerRepository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Customer Data");

        // Header
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Name");
        header.createCell(2).setCellValue("Email");
        header.createCell(3).setCellValue("Mobile Number");

        // Data rows
        int rowCount = 1;
        for (Customer c : customers) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(c.getId());
            row.createCell(1).setCellValue(c.getCustomerName());
            row.createCell(2).setCellValue(c.getEmail());
            row.createCell(3).setCellValue(c.getMobileNumber());
        }

        // Set response headers
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=customers.xlsx");

        // Write to response output stream
        try (ServletOutputStream out = response.getOutputStream()) {
            workbook.write(out);
        }

        workbook.close();
    }
}

