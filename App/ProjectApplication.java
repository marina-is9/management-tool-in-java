package com.example.project.controller;

import com.example.project.dto.TaskDTO;
import com.example.project.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // Get all tasks in a project
    @GetMapping
    public List<TaskDTO> getTasks(@PathVariable Long projectId) {
        return taskService.getTasks(projectId);
    }

    // Get backlog of tasks for a project
    @GetMapping("/backlog")
    public List<TaskDTO> getBacklog(@PathVariable Long projectId) {
        return taskService.getBacklog(projectId);
    }

    // Get tasks in a specific sprint
    @GetMapping("/sprints/{sprintId}")
    public List<TaskDTO> getSprintTasks(@PathVariable Long projectId, @PathVariable Long sprintId) {
        return taskService.getSprintTasks(projectId, sprintId);
    }

    // Create a new task
    @PostMapping
    public TaskDTO create(@PathVariable Long projectId, @RequestBody TaskDTO taskDTO) {
        return taskService.createTask(projectId, taskDTO);
    }

    // View a specific task
    @GetMapping("/{taskId}")
    public TaskDTO viewTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        return taskService.viewTask(projectId, taskId);
    }

    // Update a specific task
    @PutMapping("/{taskId}")
    public TaskDTO updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
        return taskService.updateTask(projectId, taskId, taskDTO);
    }

    // Delete a specific task
    @DeleteMapping("/{taskId}")
    public void removeTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.removeTask(projectId, taskId);
    }

    // Get subtasks of a task
    @GetMapping("/{taskId}/subtasks")
    public List<TaskDTO> getSubTasks(@PathVariable Long projectId, @PathVariable Long taskId) {
        return taskService.getSubTasks(projectId, taskId);
    }

    // Assign a task to an employee
