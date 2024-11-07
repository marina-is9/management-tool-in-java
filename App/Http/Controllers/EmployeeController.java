package com.example.controller;

import com.example.api.ResponseCode;
import com.example.api.ResponseHandler;
import com.example.service.AuthManager;
import com.example.repository.EmployeeRepository;
import com.example.repository.SprintRepository;
import com.example.model.Employee;
import com.example.model.EmployeeDTO;
import com.example.model.EmployeeId;
import com.example.model.SprintId;
import com.example.factory.EmployeeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final SprintRepository sprintRepository;
    private final AuthManager authManager;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository, SprintRepository sprintRepository, AuthManager authManager) {
        this.employeeRepository = employeeRepository;
        this.sprintRepository = sprintRepository;
        this.authManager = authManager;
    }

    @GetMapping
    public ResponseEntity<?> viewAllEmployees() {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Get all employees
            List<Employee> employees = employeeRepository.viewAllEmployees();

            // Return success response
            return ResponseHandler.success(ResponseCode.SUCCESS, employees);
        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "Failed to retrieve employees", e.getMessage(), null);
        }
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Create Employee model using factory
            Employee employee = EmployeeFactory.create(employeeDTO);

            // Store employee in the database
            Employee savedEmployee = employeeRepository.store(employee);

            // Return success response
            return ResponseHandler.success(ResponseCode.CREATED, savedEmployee);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "Invalid input", e.getMessage(), null);
        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "Employee creation failed", e.getMessage(), null);
        }
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<?> viewEmployee(@PathVariable Long employeeId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Create EmployeeId object
            EmployeeId employeeIdObj = new EmployeeId(employeeId);

            // Find employee by ID
            Employee employee = employeeRepository.getByEmployeeId(employeeIdObj);

            // Return success response
            return ResponseHandler.success(ResponseCode.SUCCESS, employee);
        } catch (ModelNotFoundException e) {
            return ResponseHandler.error(ResponseCode.NOT_FOUND, "Employee not found", e.getMessage(), null);
        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "Failed to retrieve employee", e.getMessage(), null);
        }
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Set employee ID in the DTO
            employeeDTO.setId(employeeId);

            // Create Employee model using factory
            Employee employee = EmployeeFactory.create(employeeDTO);

            // Update employee in the database
            Employee updatedEmployee = employeeRepository.update(employee);

            // Return success response
            return ResponseHandler.success(ResponseCode.SUCCESS, updatedEmployee);
        } catch (ModelNotFoundException e) {
            return ResponseHandler.error(ResponseCode.NOT_FOUND, "Employee not found", e.getMessage(), null);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, "Invalid input", e.getMessage(), null);
        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "Employee update failed", e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Create EmployeeId object
            EmployeeId employeeId = new EmployeeId(id);

            // Delete employee
            employeeRepository.delete(employeeId);

            // Return success response
            return ResponseHandler.success(ResponseCode.SUCCESS, "Employee deleted successfully");
        } catch (ModelNotFoundException e) {
            return ResponseHandler.error(ResponseCode.NOT_FOUND, "Employee not found", e.getMessage(), null);
        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "Failed to delete employee", e.getMessage(), null);
        }
    }

    @GetMapping("/managers")
    public ResponseEntity<?> getManagers() {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Get managers
            List<Employee> managers = employeeRepository.getManagers();

            // Return success response
            return ResponseHandler.success(ResponseCode.SUCCESS, managers);
        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "Failed to retrieve managers", e.getMessage(), null);
        }
    }

    @GetMapping("/{employeeId}/sprints/{sprintId}/hours")
    public ResponseEntity<?> calculateEmployeeSprintHours(@PathVariable Long employeeId, @PathVariable Long sprintId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Create EmployeeId and SprintId objects
            EmployeeId employeeIdObj = new EmployeeId(employeeId);
            SprintId sprintIdObj = new SprintId(sprintId);

            // Retrieve employee and sprint data
            Employee employee = employeeRepository.getByEmployeeId(employeeIdObj);
            Sprint sprint = sprintRepository.getBySprintId(sprintIdObj);

            // Calculate sprint hours
            int hours = employeeRepository.calculateEmployeeSprintHours(employee, sprint);

            // Return success response
            return ResponseHandler.success(ResponseCode.SUCCESS, hours);
        } catch (ModelNotFoundException e) {
            return ResponseHandler.error(ResponseCode.NOT_FOUND, "Employee or sprint not found", e.getMessage(), null);
        } catch (Exception e) {
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, "Failed to calculate sprint hours", e.getMessage(), null);
        }
    }
}
