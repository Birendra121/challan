package com.avis.trcModule;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.avis.exception.NoRecordFoundException;



@Controller
@RequestMapping("/trc")
public class RCController {

    @Autowired
    private VehicleRCFetcherService fetcherService;
    
  //  @Autowired
   // private ExcelExportService excelExportService;
    @Autowired
    private TRCExcelExportService excelExportService;
    
    @Autowired
    private VehicleRCRepository repository;
    
    private List<VehicleRCDetail> lastProcessedList = new ArrayList<>();

    @GetMapping("/trcindex")
    public String index(Model model) {
        return "trcindex";
    }

	
	
    @PostMapping("/trc-file-upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        List<VehicleRCDetail> details = fetcherService.processExcelAndFetchRC(file);
        lastProcessedList = details;
        redirectAttributes.addFlashAttribute("rcDetails", details);
        redirectAttributes.addFlashAttribute("showDownloadLink", true);
        return "redirect:/trc/trcindex";
    }
    
    @GetMapping("/download-last-excel")
    public void downloadLastProcessed(HttpServletResponse response) throws IOException {
        excelExportService.exportListToExcel(response, lastProcessedList);
    }
    
    
    // handler method to get challan by single vehicle number
    /*
    @PostMapping("/fetchSingleRC")
    public String fetchSingleRC(@RequestParam("vehicleNumber") String vehicleNumber,RedirectAttributes redirectAttributes, Model model) throws Exception {
        VehicleRCDetail rcDetail = fetcherService.fetchSingleRCDetail(vehicleNumber);

        if (rcDetail != null) {
          //  model.addAttribute("singleRcDetail", rcDetail);
        	redirectAttributes.addFlashAttribute("singleRcDetail", rcDetail);
        } else {
           // model.addAttribute("error", "No RC details found for: " + vehicleNumber);
        	redirectAttributes.addFlashAttribute("error", "No RC details found for: " + vehicleNumber);
        }
       // return "index";
        return "redirect:/trc/trcindex";
    }
*/
    @PostMapping("/fetchSingleRC")
    public String fetchSingleRC(@RequestParam("vehicleNumber") String vehicleNumber,
                                RedirectAttributes redirectAttributes) {
        try {
            VehicleRCDetail rcDetail = fetcherService.fetchSingleRCDetail(vehicleNumber);

         // If record is returned successfully, add to flash attributes
            redirectAttributes.addFlashAttribute("singleRcDetail", rcDetail);
            
			/*
			 * if (rcDetail != null) {
			 * redirectAttributes.addFlashAttribute("singleRcDetail", rcDetail); } else {
			 * redirectAttributes.addFlashAttribute("error",
			 * "No Record Found for vehicle number: " + vehicleNumber); }
			 */
        } catch (NoRecordFoundException e) {
            // Specific handling for no data found
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            
        } catch (Exception e) {
            e.printStackTrace();  // For debug: print stack trace to console
            redirectAttributes.addFlashAttribute("error", "An error occurred while fetching details. Please try again.");
        }

        return "redirect:/trc/trcindex";
    }

    
    @GetMapping("/result")
    public String showResult(Model model) {
        // "rcDetails" will be available from FlashAttributes after redirect
        return "result";
    }

    // handler method to download excel report from db (all data)
    
    @GetMapping("/download-excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
        excelExportService.exportToExcel(response);
    }
    
    //---------

}

