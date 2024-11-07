package com.example.project.dto;

import com.example.project.services.AuthManager;

public class RoleDTO {

    private Long id;
    private String name;
    private String tenantId;

    public RoleDTO(RoleRequest request) {
        this.id = request.getId() != null ? request.getId() : null;
        this.name = request.getName();
        this.tenantId = AuthManager.getTenantId(); // Assuming AuthManager is a service to fetch tenant info
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
