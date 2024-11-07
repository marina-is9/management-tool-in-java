package com.example.project.request;

import java.time.LocalDateTime;

public class TaskRequest {

    private Long id;
    private String title;
    private String description;
    private Integer progress;
    private int projectId;
    private Integer priorityId;
    private Integer sprintId;
    private String startedAt;
    private String deadline;
    private String completedAt;
    private Integer repeatDays;
    private String explanationClipPath;
    private Integer meetingCount;
    private Integer duration;
    private Integer statusId;
    private Integer dependsOnId;

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

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public Integer getSprintId() {
        return sprintId;
    }

    public void setSprintId(Integer sprintId) {
        this.sprintId = sprintId;
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

    public Integer getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(Integer repeatDays) {
        this.repeatDays = repeatDays;
    }

    public String getExplanationClipPath() {
        return explanationClipPath;
    }

    public void setExplanationClipPath(String explanationClipPath) {
        this.explanationClipPath = explanationClipPath;
    }

    public Integer getMeetingCount() {
        return meetingCount;
    }

    public void setMeetingCount(Integer meetingCount) {
        this.meetingCount = meetingCount;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getDependsOnId() {
        return dependsOnId;
    }

    public void setDependsOnId(Integer dependsOnId) {
        this.dependsOnId = dependsOnId;
    }
}
