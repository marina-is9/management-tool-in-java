package com.example.project.dto;

import com.example.project.services.AuthManager;

import java.time.LocalDateTime;

public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private int progress;
    private int projectId;
    private int priorityId;
    private Integer sprintId;
    private String tenantId;
    private Integer dependsOnId;
    private String startedAt;
    private String deadline;
    private String completedAt;
    private int repeatDays;
    private String explanationClipPath;
    private int meetingCount;
    private int duration;
    private int statusId;

    public TaskDTO(TaskRequest request) {
        this.id = request.getId() != null ? request.getId() : null;
        this.title = request.getTitle();
        this.description = request.getDescription();
        this.progress = request.getProgress() != null ? request.getProgress() : 0;
        this.projectId = request.getProjectId();
        this.priorityId = request.getPriorityId() != null ? request.getPriorityId() : 1;
        this.sprintId = request.getSprintId() != null ? request.getSprintId() : null;
        this.tenantId = AuthManager.getTenantId(); // Assuming AuthManager is a service to fetch tenant info
        this.dependsOnId = request.getDependsOnId() != null ? request.getDependsOnId() : null;
        this.startedAt = request.getStartedAt().toString();
        this.deadline = request.getDeadline() != null ? request.getDeadline().toString() : null;
        this.completedAt = request.getCompletedAt() != null ? request.getCompletedAt().toString() : null;
        this.repeatDays = request.getRepeatDays() != null ? request.getRepeatDays() : 0;
        this.explanationClipPath = request.getExplanationClipPath() != null ? request.getExplanationClipPath() : "";
        this.meetingCount = request.getMeetingCount() != null ? request.getMeetingCount() : 0;
        this.duration = request.getDuration() != null ? request.getDuration() : 0;
        this.statusId = request.getStatusId() != null ? request.getStatusId() : 1;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getSprintId() {
        return sprintId;
    }

    public void setSprintId(Integer sprintId) {
        this.sprintId = sprintId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDependsOnId() {
        return dependsOnId;
    }

    public void setDependsOnId(Integer dependsOnId) {
        this.dependsOnId = dependsOnId;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public int getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(int repeatDays) {
        this.repeatDays = repeatDays;
    }

    public String getExplanationClipPath() {
        return explanationClipPath;
    }

    public void setExplanationClipPath(String explanationClipPath) {
        this.explanationClipPath = explanationClipPath;
    }

    public int getMeetingCount() {
        return meetingCount;
    }

    public void setMeetingCount(int meetingCount) {
        this.meetingCount = meetingCount;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
