package com.avis.pagePermission;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avis.entity.User;

import lombok.Data;

@Data
@Entity
@Table(name = "page_permissions")
public class PagePermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pageName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Constructors
    public PagePermission() {}

    public PagePermission(User user, String pageName) {
        this.user = user;
        this.pageName = pageName;
    }

    // Getters and setters...
}


