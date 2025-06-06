package com.avis.pagePermission;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avis.entity.Role;
import com.avis.entity.User;
import com.avis.repository.UserRepository;

@Service
public class PagePermissionService {

    @Autowired
    private PagePermissionRepository pagePermissionRepository;

    @Autowired
    private UserRepository userRepository;

    // Existing method: get pages by userId
    public List<String> getPagesByUserId(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<PagePermission> permissions = pagePermissionRepository.findByUser(user);
        return permissions.stream()
                          .map(PagePermission::getPageName)
                          .toList();
    }

    // New method: get pages by username (for easier use in controllers)
    public List<String> getAllowedPagesForUser(String username) {
        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        List<PagePermission> permissions = pagePermissionRepository.findByUser(user);
        return permissions.stream()
                          .map(PagePermission::getPageName)
                          .toList();
    }

    // Existing method: save permissions by userId
    @Transactional
    public void savePagePermissions(Long userId, List<String> pageNames) {
        // First, delete existing permissions
        pagePermissionRepository.deleteByUserId(userId);

        // Then, add new permissions
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        List<PagePermission> newPermissions = pageNames.stream()
            .map(page -> new PagePermission(user, page))
            .toList();
        pagePermissionRepository.saveAll(newPermissions);
    }
    
}
