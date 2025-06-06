package com.avis.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	
	
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String role = authority.getAuthority();
            System.out.println("Logged-in role: " + role);  // ✅ For debugging
            
            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/index");  // Admin page
                return;
            } else if (role.equals("ROLE_USER")) {
                response.sendRedirect("/user-home");  // User-only page
                return;
            }
        }

        // Default fallback
        response.sendRedirect("/index");
    }
    
//------    
	
   
}
