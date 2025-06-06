package com.avis.exception;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.avis.entity.User;
import com.avis.pagePermission.PagePermissionService;
import com.avis.repository.UserRepository;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PagePermissionService pagePermissionService;
	
	@ExceptionHandler(NoChallanFoundException.class)
    public String handleNoChallanFound(NoChallanFoundException e, Model model) {
		logger.warn("NoChallanFoundException: {}", e.getMessage());
        model.addAttribute("noChalanMessage", "No challan found for the given details.");
        return "vehicleForm"; // Redirect back to the form with no-challan message
    }
	
	@ExceptionHandler(Exception.class)
	public String handleGenericException(Exception e, Model model) {
		 logger.error("Exception: {}", e.getMessage());
		model.addAttribute("errorMessage", "An unexpected error occurred. Please try again later.");
		return "errorPage";
	}
	
	//-------------
	// method for pagePermission module
	@ModelAttribute
    public void populateUserPermissions(Model model, Principal principal) {
        if (principal != null) {
            String email = principal.getName();
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                boolean isAdmin = user.getRoles().stream()
                                      .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
                List<String> allowedPages = pagePermissionService.getAllowedPagesForUser(email);
                model.addAttribute("isAdmin", isAdmin);
                model.addAttribute("allowedPages", allowedPages);
            }
        }
    }
	
}
