package com.avis.userentryModule;


import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avis.pagePermission.PagePermissionService;

@Controller
public class CustomerController {

    private final CustomerExcelService excelService;
    
    @Autowired
    private CompanyLogoService companyLogoService;
    
    @Autowired
    private PagePermissionService pagePermissionService;
    
    @Autowired
    private CustomerExportService customerExportService;

    public CustomerController(CustomerExcelService excelService) {
        this.excelService = excelService;
    }

	/*
	  @GetMapping("/customer") 
	  public String showForm(Model model) {
	  model.addAttribute("user", new Customer());
	  
	  model.addAttribute("navLogos", companyLogoService.getLogo());
	  model.addAttribute("customer", new Customer());
	  
	  return "user-form"; 
	  }
	 */
    
	  @GetMapping("/customer") 
	  public String showForm(Model model, Principal principal) {
		  if(principal !=null) {
			  model.addAttribute("user", principal.getName());
			  model.addAttribute("user", new Customer());
			  
			  List<String> allowedPages = pagePermissionService.getAllowedPagesForUser(principal.getName());
			  
			  model.addAttribute("navLogos", companyLogoService.getLogo());
			  model.addAttribute("customer", new Customer());
			  
			  model.addAttribute("allowedPages", allowedPages);
		  }
	  
	  return "user-form"; 
	  }
    
//------
	
    
	
    
    @PostMapping("/customer/submit")
    @ResponseBody
    public String submitForm(@ModelAttribute Customer customer, Model model) {
        excelService.writeToExcel(customer);
        excelService.saveCustomer(customer);
        model.addAttribute("message", "User details saved successfully!");
      //  return "user-form";
        return "User details saved successfully!";
    }
    
    // handler method to dowload customer details in excel file
    @GetMapping("/customer/download")
    public void downloadExcel(HttpServletResponse response) throws IOException {
    	customerExportService.exportToExcel(response);
    }
}

