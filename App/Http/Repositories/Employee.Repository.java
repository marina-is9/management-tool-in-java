package com.example.repository;

import com.example.model.Employee;
import com.example.model.Sprint;
import com.example.model.Role;
import com.example.model.Position;
import com.example.exception.ModelNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private EmployeeJpaRepository employeeJpaRepository;

    @Transactional
    public Employee store(Employee employee) throws ModelNotFoundException {
        try {
            // Prepare and persist the new employee
            entityManager.persist(employee);
            return employee;
        } catch (Exception e) {
            throw new ModelNotFoundException("Employee creation failed. " + e.getMessage(), e);
        }
    }

    @Transactional
    public Employee update(Employee employee) throws ModelNotFoundException {
        try {
            // Check if the employee exists, otherwise throw exception
            Optional<Employee> existingEmployee = employeeJpaRepository.findById(employee.getId());
            if (!existingEmployee.isPresent()) {
                throw new ModelNotFoundException("Employee with ID " + employee.getId() + " not found.");
            }

            // Update employee information
            return employeeJpaRepository.save(employee);
        } catch (Exception e) {
            throw new ModelNotFoundException("Employee update failed. " + e.getMessage(), e);
        }
    }

    public Employee getByEmployeeId(Long employeeId) throws ModelNotFoundException {
        try {
            return employeeJpaRepository.findById(employeeId)
                    .orElseThrow(() -> new ModelNotFoundException("Employee not found with ID " + employeeId));
        } catch (Exception e) {
            throw new ModelNotFoundException("Error retrieving employee. " + e.getMessage(), e);
        }
    }

    @Transactional
    public boolean delete(Long employeeId) throws ModelNotFoundException {
        try {
            Employee employee = employeeJpaRepository.findById(employeeId)
                    .orElseThrow(() -> new ModelNotFoundException("Employee not found for deletion."));

            employeeJpaRepository.delete(employee);
            return true;
        } catch (Exception e) {
            throw new ModelNotFoundException("Error deleting employee. " + e.getMessage(), e);
        }
    }

    public List<Employee> viewAllEmployees() {
        try {
            return employeeJpaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve employees. " + e.getMessage(), e);
        }
    }

    public Object calculateEmployeeSprintHours(Employee employee, Sprint sprint) {
        try {
            // Assuming SprintTasks is a field in Employee, or another entity related to employee tasks
            List<Task> sprintTasks = employee.getTasks().stream()
                    .filter(task -> task.getSprint().getId().equals(sprint.getId()))
                    .collect(Collectors.toList());

            // Calculate the total hours assigned in the sprint
            double sprintHours = sprintTasks.stream().mapToDouble(Task::getDuration).sum();

            // Calculate the number of days in the sprint
            long sprintDays = ChronoUnit.DAYS.between(sprint.getStartDate().atStartOfDay(), sprint.getEndDate().atStartOfDay()) + 1;

            // Calculate the maximum allowable man-hours for the employee in the sprint
            double maxManHours = employee.getDailyManHours() * sprintDays;

            return new SprintHours(sprintHours, maxManHours);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate sprint hours for employee. " + e.getMessage(), e);
        }
    }

    public List<Employee> getManagers() throws ModelNotFoundException {
        try {
            // Query the manager role ID using the Position enum
            Long managerRoleId = entityManager.createQuery("SELECT r.id FROM Role r WHERE r.name = :roleName", Long.class)
                    .setParameter("roleName", Position.MANAGER.getLabel())
                    .getSingleResult();

            // Find all employees with the manager role
            TypedQuer
