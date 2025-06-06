package com.avis.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.avis.entity.Role;
import com.avis.entity.User;
import com.avis.pagePermission.PagePermissionService;
import com.avis.repository.RoleRepository;
import com.avis.repository.UserRepository;
import com.avis.security.UserDetailsServiceImpl;

@Controller
public class UserController {
	
	@Autowired
	private UserDetailsServiceImpl userService;
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PagePermissionService pagePermissionService;
    
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/index")
    public String showIndexPage(Model model) {
        return "index"; // This returns the index.html template
    }
    
    @GetMapping("/login")
    public String login(Model model, String logout, String error) {
    	if(logout !=null) {
    		model.addAttribute("logoutMessage", "You have been logged out successfully.");
    	}
    	if(error !=null) {
    		model.addAttribute("error", "Your username and password are invalid.");
    	}
        return "login";
    }
    
    @GetMapping("/error")
    public String handleError() {
        return "error"; // Name of the error page template
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
    	model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
        // Check if user already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            model.addAttribute("error", "User already exists with this email");
            return "register";
        }

        // Ensure the role is in the correct format (e.g., "ROLE_ADMIN")
        String rawRole = user.getRole();  // comes from form (e.g., "ADMIN" or "USER")
        String fullRoleName = rawRole.startsWith("ROLE_") ? rawRole : "ROLE_" + rawRole.toUpperCase();

        // Fetch role from database only
        Role role = roleRepository.findByName(fullRoleName);
        if (role == null) {
            model.addAttribute("error", "Role not found: " + fullRoleName);
            return "register";
        }

        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        
        // Add flash message
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please login.");
        
        return "redirect:/index";
    }

    
    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        // Return the view name for the password change form
        return "change-password";
    }
    
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 Principal principal, Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "New passwords do not match.");
            return "change-password"; // The name of the Thymeleaf template
        }

        try {
        	
            userService.changePassword(principal.getName(), currentPassword, newPassword);
            //return "redirect:/home";
        	model.addAttribute("successMessage", "Password changed successfully !! & Please try to Login");
        		       	
            return "change-password";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "change-password";
        }
    }
    
    //---new code---
 // User landing page after login
   /* 
    @GetMapping("/user-home")
    public String userHome(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "user-home";
    }
    */
//----------
    @GetMapping("/user-home")
    public String userHome(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("username", username);

        // Get the allowed pages for this user dynamically
        List<String> allowedPages = pagePermissionService.getAllowedPagesForUser(username);
        model.addAttribute("allowedPages", allowedPages);
        


        return "user-home";
    }

   

}
