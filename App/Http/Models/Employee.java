package com.example.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "join_date")
    private String joinDate;

    @Column(name = "leave_date")
    private String leaveDate;

    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "reports_to_id")
    private Long reportsToId;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "daily_man_hours")
    private Integer dailyManHours;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "responsibility_id")
    private Long responsibilityId;

    @ManyToOne
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
    private Tenant tenant;

    @ManyToMany
    @JoinTable(
        name = "employee_skill",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;

    @ManyToMany
    @JoinTable(
        name = "team_employee",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams;

    @ManyToMany
    @JoinTable(
        name = "project_manager",
        joinColumns = @JoinColumn(name = "manager_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> managerProjects;

    @ManyToMany
    @JoinTable(
        name = "employee_project",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects;

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL)
    private Set<Event> hostedEvents;

    @ManyToMany
    @JoinTable(
        name = "event_employees",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events;

    @ManyToMany
    @JoinTable(
        name = "employee_task",
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private Set<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "responsibility_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Responsibility responsibility;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EmployeeStatus status;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Other getters and setters omitted for brevity
}
