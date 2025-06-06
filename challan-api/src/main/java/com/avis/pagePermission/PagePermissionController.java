package com.avis.pagePermission;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.avis.entity.User;
import com.avis.repository.UserRepository;

@Controller
@RequestMapping("/admin")
public class PagePermissionController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PagePermissionService pagePermissionService;

    // List of all Thymeleaf page names (hardcoded or fetched dynamically)
    private static final List<String> ALL_PAGES = List.of(
         "/register", "/trc/trcindex",
        "/challan/home", "/customer"
        // Add your real Thymeleaf page URLs here
    );
/*
    @GetMapping("/assign-permissions")
    public String showAssignPermissionsPage(Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("allPages", ALL_PAGES);
        return "assign-permissions";  // your Thymeleaf page for assigning permissions
    }
    */
    
    @GetMapping("/assign-permissions")
    public String showAssignPermissionsPage(Model model) {
        List<User> nonAdminUsers = userRepository.findAllByRoleNameNot("ROLE_ADMIN");
        model.addAttribute("users", nonAdminUsers);
        model.addAttribute("allPages", ALL_PAGES);
        return "assign-permissions";  // Thymeleaf template
    }


    @GetMapping("/user-permissions")
    @ResponseBody
    public List<String> getUserPermissions(@RequestParam Long userId) {
        return pagePermissionService.getPagesByUserId(userId);
    }

    @PostMapping("/save-permissions")
    @ResponseBody
    public String saveUserPermissions(@RequestParam Long userId, @RequestParam(required = false) List<String> pages) {
    	if(pages == null) {
    		pages = new ArrayList<>(); // Treat null as empty
    	}
        pagePermissionService.savePagePermissions(userId, pages);
        return "Permissions updated successfully";
    }
}
