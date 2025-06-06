package com.avis.userentryModule;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class LogoUploadController {

    private static final String UPLOAD_DIR = "uploads/";

    @Autowired
    private CompanyLogoService companyLogoService;
    
   


    @GetMapping("/admin/logo/upload")
    public String showLogoUploadForm(Model model) {
        model.addAttribute("currentLogo", companyLogoService.getLogo());
        return "logo-upload-form";  // your Thymeleaf template for upload form
    }

    @PostMapping("/admin/logo/upload")
    public String handleLogoUpload(@RequestParam("leftLogoFile") MultipartFile leftLogoFile,
                                   @RequestParam("rightLogoFile") MultipartFile rightLogoFile) throws IOException {

        // Ensure upload directory exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique file names
        String leftLogoFilename = UUID.randomUUID() + "_" + leftLogoFile.getOriginalFilename();
        String rightLogoFilename = UUID.randomUUID() + "_" + rightLogoFile.getOriginalFilename();

        // Save files to disk
        Files.copy(leftLogoFile.getInputStream(), uploadPath.resolve(leftLogoFilename), StandardCopyOption.REPLACE_EXISTING);
        Files.copy(rightLogoFile.getInputStream(), uploadPath.resolve(rightLogoFilename), StandardCopyOption.REPLACE_EXISTING);

        // Save paths to DB (paths are web-accessible relative paths)
        CompanyLogo logo = new CompanyLogo();
        logo.setId(1L);  // always single logo row
        logo.setLeftLogoPath("/uploads/" + leftLogoFilename);
        logo.setRightLogoPath("/uploads/" + rightLogoFilename);

        companyLogoService.saveLogo(logo);

        return "redirect:/admin/logo/upload";
    }
}
