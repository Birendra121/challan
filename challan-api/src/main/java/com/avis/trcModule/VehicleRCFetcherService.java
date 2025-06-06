package com.avis.trcModule;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class VehicleRCFetcherService {

    
	
    private final VehicleRCRepository repository;
    private final VehicleRCAPIRetryService retryService;

    @Autowired
    public VehicleRCFetcherService(VehicleRCRepository repository, VehicleRCAPIRetryService retryService) {
        this.repository = repository;
        this.retryService = retryService;
    }

 // ✅ Memory store for last processed batch
    
    /*
    private List<VehicleRCDetail> lastProcessedBatch = new ArrayList<>();
    
    public List<VehicleRCDetail> processExcelAndFetchRC(MultipartFile file) {
        List<VehicleRCDetail> savedData = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String vehicleNumber = cell.getStringCellValue().trim().replaceAll("\\s+", "");

                    if (!vehicleNumber.isEmpty() && vehicleNumber.length() <= 12) {
                        try {
                            VehicleRCDetail newDetail = retryService.fetchRCDetail(vehicleNumber); // ✅ now retryable
                            if (newDetail != null) {
                                VehicleRCDetail existing = repository.findByRegistrationNumber(vehicleNumber);
                                if (existing == null) {
                                    savedData.add(repository.save(newDetail));
                                } else if (hasChanged(existing, newDetail)) {
                                    newDetail.setId(existing.getId()); // preserve ID for update
                                    savedData.add(repository.save(newDetail));
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Retry failed for vehicle number: " + vehicleNumber);
                        }
                    } else {
                        System.out.println("Skipped invalid vehicle number: " + vehicleNumber);
                    }
                }
            }

            lastProcessedBatch = new ArrayList<>(savedData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedData;
    }
    
    */
    public List<VehicleRCDetail> getLastProcessedBatch() {
        return lastProcessedBatch;
    }

    //---new code
    
    private List<VehicleRCDetail> lastProcessedBatch = new ArrayList<>();

    public List<VehicleRCDetail> processExcelAndFetchRC(MultipartFile file) {
        List<VehicleRCDetail> savedData = new ArrayList<>();

        try (InputStream is = file.getInputStream(); Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String vehicleNumber = cell.getStringCellValue().trim().replaceAll("\\s+", "");

                    if (!vehicleNumber.isEmpty() && vehicleNumber.length() <= 12) {
                        try {
                            VehicleRCDetail existing = repository.findByRegistrationNumber(vehicleNumber);

                            if (existing != null) {
                                savedData.add(existing); // Already fetched and unchanged, skip API
                                System.out.println("Fetched from DB: " + vehicleNumber);
                            } else {
                                VehicleRCDetail newDetail = retryService.fetchRCDetail(vehicleNumber);
                                if (newDetail != null) {
                                    savedData.add(repository.save(newDetail));
                                    System.out.println("Fetched from Zoop API (new): " + vehicleNumber);
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Retry failed for vehicle number: " + vehicleNumber);
                        }
                    } else {
                        System.out.println("Skipped invalid vehicle number: " + vehicleNumber);
                    }
                }
            }

            lastProcessedBatch = new ArrayList<>(savedData);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return savedData;
    }

    // --------end new code
    
    private boolean hasChanged(VehicleRCDetail oldDetail, VehicleRCDetail newDetail) {
        return !oldDetail.equals(newDetail); // Consider overriding equals() for deep comparison
    }
    
    
    //---method to get single rc details
    
    /*
    public VehicleRCDetail fetchSingleRCDetail(String vehicleNumber) throws Exception {
        vehicleNumber = vehicleNumber.trim().replaceAll("\\s+", "");

        if (vehicleNumber.isEmpty() || vehicleNumber.length() > 12) {
            System.out.println("Invalid vehicle number: " + vehicleNumber);
            return null;
        }

        VehicleRCDetail newDetail = retryService.fetchRCDetail(vehicleNumber);  // ✅ use retry service
        if (newDetail != null) {
            VehicleRCDetail existing = repository.findByRegistrationNumber(vehicleNumber);
            if (existing == null) {
                return repository.save(newDetail);
            } else if (hasChanged(existing, newDetail)) {
                newDetail.setId(existing.getId()); // Preserve ID for update
                return repository.save(newDetail);
            } else {
                return existing; // Return existing if no change
            }
        }

        return null;
    }
*/
    
    public VehicleRCDetail fetchSingleRCDetail(String vehicleNumber) throws Exception {
        vehicleNumber = vehicleNumber.trim().replaceAll("\\s+", "");

        if (vehicleNumber.isEmpty() || vehicleNumber.length() > 12) {
            System.out.println("Invalid vehicle number: " + vehicleNumber);
            return null;
        }

        VehicleRCDetail existing = repository.findByRegistrationNumber(vehicleNumber);
        if (existing != null) {
            System.out.println("Fetched from DB: " + vehicleNumber);
            return existing; // ✅ Return from DB to save API cost
        }

        // ❗Only call API if not already saved
        VehicleRCDetail newDetail = retryService.fetchRCDetail(vehicleNumber);
        if (newDetail != null) {
            System.out.println("Fetched from Zoop API: " + vehicleNumber);
            return repository.save(newDetail);
        }

        return null;
    }
    
    
    // method to single rc details, with hasChange method
 /*   
    public VehicleRCDetail fetchSingleRCDetail(String vehicleNumber) throws Exception {
        vehicleNumber = vehicleNumber.trim().replaceAll("\\s+", "");

        if (vehicleNumber.isEmpty() || vehicleNumber.length() > 12) {
            System.out.println("Invalid vehicle number: " + vehicleNumber);
            return null;
        }

        VehicleRCDetail existing = repository.findByRegistrationNumber(vehicleNumber);
        if (existing != null) {
            VehicleRCDetail newDetail = retryService.fetchRCDetail(vehicleNumber);
            if (newDetail != null && hasChanged(existing, newDetail)) {
                newDetail.setId(existing.getId());
                System.out.println("Updated from Zoop API: " + vehicleNumber);
                return repository.save(newDetail);
            } else {
                System.out.println("Fetched from DB (no change): " + vehicleNumber);
                return existing;
            }
        }

        // Not found in DB, fetch new
        VehicleRCDetail newDetail = retryService.fetchRCDetail(vehicleNumber);
        if (newDetail != null) {
            System.out.println("Fetched from Zoop API (new): " + vehicleNumber);
            return repository.save(newDetail);
        }

        return null;
    }
*/

}
