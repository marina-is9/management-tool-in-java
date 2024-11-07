package com.example.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthManager {

    // Method to authenticate the user
    public static void authenticateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }

    // Method to get Tenant ID from the authenticated user
    public static String getTenantId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tenant ID not found.");
        }

        User user = (User) authentication.getPrincipal();
        String tenantId = user.getUsername();  // Assuming tenant_id is stored in the username or other attributes

        if (tenantId == null || tenantId.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tenant ID not found.");
        }

        return tenantId;
    }
}
