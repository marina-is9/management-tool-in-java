package com.example.project.controller;

import com.example.project.dto.TaskDTO;
import com.example.project.exception.InvalidArgumentException;
import com.example.project.exception.ModelNotFoundException;
import com.example.project.model.Task;
import com.example.project.repository.ProjectRepository;
import com.example.project.repository.TaskRepository;
import com.example.project.service.AuthManager;
import com.example.project.service.ResponseHandler;
import com.example.project.service.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private AuthManager authManager;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid TaskDTO taskDTO) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Create Task
            Task task = taskRepository.save(taskDTO.toEntity());

            return ResponseHandler.success(HttpStatus.CREATED, task);
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{projectId}/tasks")
    public ResponseEntity<?> getTasks(@PathVariable Long projectId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Validate project exists
            projectRepository.findById(projectId).orElseThrow(() -> new ModelNotFoundException("Project not found"));

            // Fetch tasks by projectId
            var tasks = taskRepository.findByProjectId(projectId);

            return ResponseHandler.success(HttpStatus.OK, tasks);
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{projectId}/backlog")
    public ResponseEntity<?> getBacklog(@PathVariable Long projectId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Validate project exists
            projectRepository.findById(projectId).orElseThrow(() -> new ModelNotFoundException("Project not found"));

            // Fetch backlog tasks by projectId
            var tasks = taskRepository.findBacklogByProjectId(projectId);

            return ResponseHandler.success(HttpStatus.OK, tasks);
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{projectId}/{sprintId}/tasks")
    public ResponseEntity<?> getSprintTasks(@PathVariable Long projectId, @PathVariable Long sprintId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Validate project and sprint
            projectRepository.findById(projectId).orElseThrow(() -> new ModelNotFoundException("Project not found"));

            // Fetch tasks for the sprint
            var tasks = taskRepository.findByProjectIdAndSprintId(projectId, sprintId);

            return ResponseHandler.success(HttpStatus.OK, tasks);
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/{projectId}/{taskId}")
    public ResponseEntity<?> updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody @Valid TaskDTO taskDTO) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Merge taskId with request
            taskDTO.setId(taskId);

            // Update task
            Task task = taskRepository.save(taskDTO.toEntity());

            return ResponseHandler.success(HttpStatus.OK, task);
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{projectId}/{taskId}")
    public ResponseEntity<?> removeTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Validate project exists
            projectRepository.findById(projectId).orElseThrow(() -> new ModelNotFoundException("Project not found"));

            // Delete task
            taskRepository.deleteById(taskId);

            return ResponseHandler.success(HttpStatus.NO_CONTENT, "Task deleted");
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{projectId}/{taskId}")
    public ResponseEntity<?> viewTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Validate project exists
            projectRepository.findById(projectId).orElseThrow(() -> new ModelNotFoundException("Project not found"));

            // Fetch task by taskId
            Task task = taskRepository.findById(taskId).orElseThrow(() -> new ModelNotFoundException("Task not found"));

            return ResponseHandler.success(HttpStatus.OK, task);
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/{employeeId}/{taskId}/assign")
    public ResponseEntity<?> assignTaskToEmployee(@PathVariable Long employeeId, @PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Assign task to employee
            taskRepository.assignTaskToEmployee(employeeId, taskId);

            return ResponseHandler.success(HttpStatus.OK, "Task assigned to employee");
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{employeeId}/{taskId}/unassign")
    public ResponseEntity<?> unassignTaskFromEmployee(@PathVariable Long employeeId, @PathVariable Long taskId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Unassign task from employee
            taskRepository.unassignTaskFromEmployee(employeeId, taskId);

            return ResponseHandler.success(HttpStatus.OK, "Task unassigned from employee");
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{projectId}/{taskId}/subtasks")
    public ResponseEntity<?> getSubTasks(@PathVariable Long projectId, @PathVariable Long taskId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Fetch subtasks for task
            var subTasks = taskRepository.findSubTasksByTaskId(taskId);

            return ResponseHandler.success(HttpStatus.OK, subTasks);
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getTasksByEmployee(@PathVariable Long employeeId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Fetch tasks for the employee
            var tasks = taskRepository.findTasksByEmployeeId(employeeId);

            return ResponseHandler.success(HttpStatus.OK, tasks);
        } catch (InvalidArgumentException e) {
            return ResponseHandler.error(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            return ResponseHandler.error(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
