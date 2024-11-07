package com.example.project.dto;

import com.example.project.services.AuthManager;

import java.time.LocalDateTime;

public class ProjectDTO {

    private Long id;
    private String name;
    private String description;
    private String icon;
    private String tenantId;
    private LocalDateTime startDate;
    private LocalDateTime completionDate;

    public ProjectDTO(ProjectRequest request) {
        this.id = request.getId() != null ? request.getId() : null;
        this.name = request.getName();
        this.description = request.getDescription() != null ? request.getDescription() : null;
        this.icon = request.getIcon() != null ? request.getIcon() : null;
        this.tenantId = AuthManager.getTenantId(); // Assuming AuthManager retrieves the tenant ID
        this.startDate = LocalDateTime.parse(request.getStartDate());
        this.completionDate = request.getCompletionDate() != null ? LocalDateTime.parse(request.getCompletionDate()) : null;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }
}
