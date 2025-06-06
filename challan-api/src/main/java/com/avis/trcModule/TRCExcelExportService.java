package com.avis.trcModule;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TRCExcelExportService {

    @Autowired
    private VehicleRCRepository repository;

    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<VehicleRCDetail> details = repository.findAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("RC Details");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Vehicle No");
        header.createCell(1).setCellValue("Owner");
        header.createCell(2).setCellValue("RC Status");
        header.createCell(3).setCellValue("Model");
        header.createCell(4).setCellValue("Fuel");
        header.createCell(5).setCellValue("Color");
        header.createCell(6).setCellValue("Reg Date");
        header.createCell(7).setCellValue("Expiry");
        header.createCell(8).setCellValue("vehicle_color");
        header.createCell(9).setCellValue("vehicle_fuel_description");
        header.createCell(10).setCellValue("vehicle_category_description");
        header.createCell(11).setCellValue("vehicle_class_description");
        header.createCell(12).setCellValue("user_present_address");
        header.createCell(13).setCellValue("bodyTypeDescription");
        header.createCell(14).setCellValue("rc_chassis_number");
        header.createCell(15).setCellValue("rc_engine_number");
        header.createCell(16).setCellValue("norms_description");
        header.createCell(17).setCellValue("rc_permit_number");
        header.createCell(18).setCellValue("rc_permit_type");
        header.createCell(19).setCellValue("rc_pucc_expiry_date");
        header.createCell(20).setCellValue("rc_pucc_no");
        header.createCell(21).setCellValue("rc_registration_location");
        header.createCell(22).setCellValue("rc_state_code");
        header.createCell(23).setCellValue("rc_status_as_on");
        header.createCell(24).setCellValue("rc_tax_upto");
        header.createCell(25).setCellValue("vehicle_maker_description");
        header.createCell(26).setCellValue("vehicle_manufactured_date");
        header.createCell(27).setCellValue("vehicle_cubic_capacity");
        header.createCell(28).setCellValue("vehicle_number_of_cylinders");
        header.createCell(29).setCellValue("vehicle_seating_capacity");
        header.createCell(30).setCellValue("rc_commercial_status");
        header.createCell(31).setCellValue("rto_code");
        header.createCell(32).setCellValue("financed");
        header.createCell(33).setCellValue("vehicle_type");
        header.createCell(34).setCellValue("vehicle_age");
        header.createCell(35).setCellValue("city");
        header.createCell(36).setCellValue("state");
        header.createCell(37).setCellValue("rc_fit_upto");
        header.createCell(38).setCellValue("rc_expiry_date");
        header.createCell(39).setCellValue("InsuranceCompany");
        header.createCell(40).setCellValue("insurancePolicyNumber");
        header.createCell(41).setCellValue("insuranceExpiryDate");
        header.createCell(42).setCellValue("Financer");

        int rowNum = 1;
        for (VehicleRCDetail rc : details) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rc.getRegistrationNumber());
            row.createCell(1).setCellValue(rc.getOwnerName());
            row.createCell(2).setCellValue(rc.getStatus());
            row.createCell(3).setCellValue(rc.getVehicleMakeModel());
            row.createCell(4).setCellValue(rc.getFuel());
            row.createCell(5).setCellValue(rc.getColor());
            row.createCell(6).setCellValue(rc.getRegistrationDate() != null ? rc.getRegistrationDate().toString() : "");
            row.createCell(7).setCellValue(rc.getExpiryDate() != null ? rc.getExpiryDate().toString() : "");
            row.createCell(8).setCellValue(rc.getColor());
            row.createCell(9).setCellValue(rc.getFuel());
            row.createCell(10).setCellValue(rc.getCategoryDescription());
            row.createCell(11).setCellValue(rc.getVehicleClass());
            row.createCell(12).setCellValue(rc.getAddress());
            row.createCell(13).setCellValue(rc.getBodyTypeDescription());
            row.createCell(14).setCellValue(rc.getChassisNumber());
            row.createCell(15).setCellValue(rc.getEngineNumber());
            row.createCell(16).setCellValue(rc.getNormsDescription());
            row.createCell(17).setCellValue(rc.getPermitNumber());
            row.createCell(18).setCellValue(rc.getPermitType());
            row.createCell(19).setCellValue(rc.getPuccExpiryDate());
            row.createCell(20).setCellValue(rc.getPuccNumber());
            row.createCell(21).setCellValue(rc.getRegistrationLocation());
            row.createCell(22).setCellValue(rc.getStateCode());
            row.createCell(23).setCellValue(rc.getStatusAsOn());
            row.createCell(24).setCellValue(rc.getTaxUpto());
            row.createCell(25).setCellValue(rc.getVehicleMakerDescription());
            row.createCell(26).setCellValue(rc.getManufacturedDate() != null ? rc.getManufacturedDate().toString() : "");
            row.createCell(27).setCellValue(rc.getCubicCapacity());
            row.createCell(28).setCellValue(rc.getNumberOfCylinders());
            row.createCell(29).setCellValue(rc.getSeatingCapacity());
            row.createCell(30).setCellValue(rc.getCommercialStatus());
            row.createCell(31).setCellValue(rc.getRtoCode());
            row.createCell(32).setCellValue(rc.getFinanced());
            row.createCell(33).setCellValue(rc.getVehicleType());
            row.createCell(34).setCellValue(rc.getVehicleAge());
            row.createCell(35).setCellValue(rc.getCity());
            row.createCell(36).setCellValue(rc.getState());
            row.createCell(37).setCellValue(rc.getRcFitUpto() != null ? rc.getRcFitUpto().toString() : "");
            row.createCell(38).setCellValue(rc.getExpiryDate() != null ? rc.getExpiryDate().toString() : "");
            row.createCell(39).setCellValue(rc.getInsuranceCompany());
            row.createCell(40).setCellValue(rc.getInsurancePolicyNumber());
            row.createCell(41).setCellValue(rc.getInsuranceExpiryDate() != null ? rc.getInsuranceExpiryDate().toString() : "");
            row.createCell(42).setCellValue(rc.getFinancer());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=rc_details.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
    
    // method to download latest processed file in excel
  
    //------------
    public void exportListToExcel(HttpServletResponse response, List<VehicleRCDetail> details) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("RC Details");

        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Vehicle No");
        header.createCell(1).setCellValue("Owner");
        header.createCell(2).setCellValue("RC Status");
        header.createCell(3).setCellValue("Model");
        header.createCell(4).setCellValue("Fuel");
        header.createCell(5).setCellValue("Color");
        header.createCell(6).setCellValue("Reg Date");
        header.createCell(7).setCellValue("Expiry");
        header.createCell(8).setCellValue("vehicle_color");
        header.createCell(9).setCellValue("vehicle_fuel_description");
        header.createCell(10).setCellValue("vehicle_category_description");
        header.createCell(11).setCellValue("vehicle_class_description");
        header.createCell(12).setCellValue("user_present_address");
        header.createCell(13).setCellValue("bodyTypeDescription");
        header.createCell(14).setCellValue("rc_chassis_number");
        header.createCell(15).setCellValue("rc_engine_number");
        header.createCell(16).setCellValue("norms_description");
        header.createCell(17).setCellValue("rc_permit_number");
        header.createCell(18).setCellValue("rc_permit_type");
        header.createCell(19).setCellValue("rc_pucc_expiry_date");
        header.createCell(20).setCellValue("rc_pucc_no");
        header.createCell(21).setCellValue("rc_registration_location");
        header.createCell(22).setCellValue("rc_state_code");
        header.createCell(23).setCellValue("rc_status_as_on");
        header.createCell(24).setCellValue("rc_tax_upto");
        header.createCell(25).setCellValue("vehicle_maker_description");
        header.createCell(26).setCellValue("vehicle_manufactured_date");
        header.createCell(27).setCellValue("vehicle_cubic_capacity");
        header.createCell(28).setCellValue("vehicle_number_of_cylinders");
        header.createCell(29).setCellValue("vehicle_seating_capacity");
        header.createCell(30).setCellValue("rc_commercial_status");
        header.createCell(31).setCellValue("rto_code");
        header.createCell(32).setCellValue("financed");
        header.createCell(33).setCellValue("vehicle_type");
        header.createCell(34).setCellValue("vehicle_age");
        header.createCell(35).setCellValue("city");
        header.createCell(36).setCellValue("state");
        header.createCell(37).setCellValue("rc_fit_upto");
        header.createCell(38).setCellValue("rc_expiry_date");
        header.createCell(39).setCellValue("InsuranceCompany");
        header.createCell(40).setCellValue("insurancePolicyNumber");
        header.createCell(41).setCellValue("insuranceExpiryDate");
        header.createCell(42).setCellValue("Financer");

        int rowNum = 1;
        for (VehicleRCDetail rc : details) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rc.getRegistrationNumber());
            row.createCell(1).setCellValue(rc.getOwnerName());
            row.createCell(2).setCellValue(rc.getStatus());
            row.createCell(3).setCellValue(rc.getVehicleMakeModel());
            row.createCell(4).setCellValue(rc.getFuel());
            row.createCell(5).setCellValue(rc.getColor());
            row.createCell(6).setCellValue(rc.getRegistrationDate() != null ? rc.getRegistrationDate().toString() : "");
            row.createCell(7).setCellValue(rc.getExpiryDate() != null ? rc.getExpiryDate().toString() : "");
            row.createCell(8).setCellValue(rc.getColor());
            row.createCell(9).setCellValue(rc.getFuel());
            row.createCell(10).setCellValue(rc.getCategoryDescription());
            row.createCell(11).setCellValue(rc.getVehicleClass());
            row.createCell(12).setCellValue(rc.getAddress());
            row.createCell(13).setCellValue(rc.getBodyTypeDescription());
            row.createCell(14).setCellValue(rc.getChassisNumber());
            row.createCell(15).setCellValue(rc.getEngineNumber());
            row.createCell(16).setCellValue(rc.getNormsDescription());
            row.createCell(17).setCellValue(rc.getPermitNumber());
            row.createCell(18).setCellValue(rc.getPermitType());
            row.createCell(19).setCellValue(rc.getPuccExpiryDate());
            row.createCell(20).setCellValue(rc.getPuccNumber());
            row.createCell(21).setCellValue(rc.getRegistrationLocation());
            row.createCell(22).setCellValue(rc.getStateCode());
            row.createCell(23).setCellValue(rc.getStatusAsOn());
            row.createCell(24).setCellValue(rc.getTaxUpto());
            row.createCell(25).setCellValue(rc.getVehicleMakerDescription());
            row.createCell(26).setCellValue(rc.getManufacturedDate() != null ? rc.getManufacturedDate().toString() : "");
            row.createCell(27).setCellValue(rc.getCubicCapacity());
            row.createCell(28).setCellValue(rc.getNumberOfCylinders());
            row.createCell(29).setCellValue(rc.getSeatingCapacity());
            row.createCell(30).setCellValue(rc.getCommercialStatus());
            row.createCell(31).setCellValue(rc.getRtoCode());
            row.createCell(32).setCellValue(rc.getFinanced());
            row.createCell(33).setCellValue(rc.getVehicleType());
            row.createCell(34).setCellValue(rc.getVehicleAge());
            row.createCell(35).setCellValue(rc.getCity());
            row.createCell(36).setCellValue(rc.getState());
            row.createCell(37).setCellValue(rc.getRcFitUpto() != null ? rc.getRcFitUpto().toString() : "");
            row.createCell(38).setCellValue(rc.getExpiryDate() != null ? rc.getExpiryDate().toString() : "");
            row.createCell(39).setCellValue(rc.getInsuranceCompany());
            row.createCell(40).setCellValue(rc.getInsurancePolicyNumber());
            row.createCell(41).setCellValue(rc.getInsuranceExpiryDate() != null ? rc.getInsuranceExpiryDate().toString() : "");
            row.createCell(42).setCellValue(rc.getFinancer());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=LastProcessedRCDetails.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
