package com.avis.userentryModule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyLogoService {

    @Autowired
    private CompanyLogoRepository logoRepository;

    public CompanyLogo getLogo() {
        // Always fetch single logo with ID = 1 (assumed single row)
        return logoRepository.findById(1L).orElse(null);
    }

    public void saveLogo(CompanyLogo logo) {
        logoRepository.save(logo);
    }
}

