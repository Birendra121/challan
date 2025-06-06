package com.avis.userentryModule;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company_logo")
public class CompanyLogo {

    @Id
    private Long id;

    private String leftLogoPath;
    private String rightLogoPath;

    // Constructors, getters, setters
    public CompanyLogo() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeftLogoPath() {
        return leftLogoPath;
    }

    public void setLeftLogoPath(String leftLogoPath) {
        this.leftLogoPath = leftLogoPath;
    }

    public String getRightLogoPath() {
        return rightLogoPath;
    }

    public void setRightLogoPath(String rightLogoPath) {
        this.rightLogoPath = rightLogoPath;
    }
}

