package com.avis.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avis.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
	
	User findByUsername(String username);
	
	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name <> :roleName")
    List<User> findAllByRoleNameNot(@Param("roleName") String roleName);
	
}
