package com.avis.pagePermission;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avis.entity.User;

public interface PagePermissionRepository extends JpaRepository<PagePermission, Long> {
    List<PagePermission> findByUser(User user);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM PagePermission p WHERE p.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

   // void deleteByUser(User user);
}
