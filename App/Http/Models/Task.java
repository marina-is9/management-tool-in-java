package com.example.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Integer progress;
    private Long projectId;
    private Long statusId;
    private Long priorityId;
    private java.time.LocalDateTime startedAt;
    private java.time.LocalDateTime deadline;
    private java.time.LocalDateTime completedAt;
    private String repeatDays;
    private String explanationClipPath;
    private Long dependsOnId;
    private Integer meetingCount;
    private Long sprintId;
    private Long tenantId;
    private Integer duration;

    @ManyToOne
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
    private Tenant tenant;

    @ManyToMany
    @JoinTable(
        name = "task_skill",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;

    @ManyToMany
    @JoinTable(
        name = "task_tag",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags;

    @OneToMany(mappedBy = "task")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "task")
    private Set<Checklist> checklists;

    @OneToMany(mappedBy = "task")
    private Set<Attachment> attachments;

    @ManyToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToMany
    @JoinTable(
        name = "employee_task",
        joinColumns = @JoinColumn(name = "task_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> employees;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private TaskStatus status;

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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(Long priorityId) {
        this.priorityId = priorityId;
    }

    public java.time.LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(java.time.LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public java.time.LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(java.time.LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public java.time.LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(java.time.LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public String getRepeatDays() {
        return repeatDays;
    }

    public void setRepeatDays(String repeatDays) {
        this.repeatDays = repeatDays;
    }

    public String getExplanationClipPath() {
        return explanationClipPath;
    }

    public void setExplanationClipPath(String explanationClipPath) {
        this.explanationClipPath = explanationClipPath;
    }

    public Long getDependsOnId() {
        return dependsOnId;
    }

    public void setDependsOnId(Long dependsOnId) {
        this.dependsOnId = dependsOnId;
    }

    public Integer getMeetingCount() {
        return meetingCount;
    }

    public void setMeetingCount(Integer meetingCount) {
        this.meetingCount = meetingCount;
    }

    public Long getSprintId() {
        return sprintId;
    }

    public void setSprintId(Long sprint
