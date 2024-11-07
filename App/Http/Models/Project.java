package com.example.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema.Property;

@Entity
@Table(name = "projects")
@Schema(name = "Project", description = "Represents a project in the system")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Property(readOnly = true)
    private Long id;

    @Column(nullable = false)
    @Property
    private String name;

    @Column
    @Property
    private String icon;

    @Column
    @Property
    private String description;

    @Column(name = "start_date")
    @Property(format = "date")
    private LocalDate startDate;

    @Column(name = "completion_date")
    @Property(format = "date")
    private LocalDate completionDate;

    @Column(name = "tenant_id")
    private Long tenantId;

    @ManyToOne
    @JoinColumn(name = "tenant_id", insertable = false, updatable = false)
    private Tenant tenant;

    @ManyToMany
    @JoinTable(
        name = "team_project",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams;

    @ManyToMany
    @JoinTable(
        name = "project_manager",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "manager_id")
    )
    private Set<Employee> managers;

    @ManyToMany
    @JoinTable(
        name = "project_employee",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<Employee> employees;

    @ManyToMany
    @JoinTable(
        name = "project_sprint",
        joinColumns = @JoinColumn(name = "project_id"),
        inverseJoinColumns = @JoinColumn(name = "sprint_id")
    )
    private Set<Sprint> sprints;

    @OneToMany(mappedBy = "project")
    @Where(clause = "sprint_status_id = (SELECT id FROM sprint_status WHERE status = 'Active' LIMIT 1)")
    @ArraySchema(schema = @Schema(implementation = Sprint.class))
    private Set<Sprint> activeSprints;

    @Transient
    @Property
    @ArraySchema(schema = @Schema(implementation = Sprint.class))
    public Set<Sprint> getActiveSprints() {
        return this.activeSprints;
    }

    // Getters and setters

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Set<Employee> getManagers() {
        return managers;
    }

    public void setManagers(Set<Employee> managers) {
        this.managers = managers;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Sprint> getSprints() {
        return sprints;
    }

    public void setSprints(Set<Sprint> sprints) {
        this.sprints = sprints;
    }

    public Set<Sprint> getActiveSprintsSet() {
        return activeSprints;
    }

    public void setActiveSprintsSet(Set<Sprint> activeSprints) {
        this.activeSprints = activeSprints;
    }
}
