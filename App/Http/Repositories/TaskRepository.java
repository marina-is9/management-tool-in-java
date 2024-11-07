import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TaskRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public Task store(TaskModel taskModel) {
        try {
            Task task = new Task();
            task.setTitle(taskModel.getTitle().getValue());
            task.setDescription(taskModel.getDescription() != null ? taskModel.getDescription().getValue() : null);
            task.setProgress(taskModel.getProgress().getValue());
            task.setProjectId(taskModel.getProjectId().getValue());
            task.setPriorityId(taskModel.getPriority().getValue());
            task.setSprintId(taskModel.getSprintId() != null ? taskModel.getSprintId().getValue() : null);
            task.setTenantId(taskModel.getTenantId());
            task.setDependsOnId(taskModel.getDependsOnId() != null ? taskModel.getDependsOnId().getValue() : null);
            task.setStartedAt(taskModel.getStartedAt().getValue().format("yyyy-MM-dd HH:mm:ss"));
            task.setDeadline(taskModel.getDeadline() != null ? taskModel.getDeadline().getValue().format("yyyy-MM-dd HH:mm:ss") : null);
            task.setCompletedAt(taskModel.getCompletedAt() != null ? taskModel.getCompletedAt().getValue().format("yyyy-MM-dd HH:mm:ss") : null);
            task.setRepeatDays(taskModel.getRepeatDays().getValue());
            task.setExplanationClipPath(taskModel.getExplanationClipPath() != null ? taskModel.getExplanationClipPath().getValue() : null);
            task.setMeetingCount(taskModel.getMeetingCount().getValue());
            task.setDuration(taskModel.getDuration().getValue());
            task.setStatusId(taskModel.getStatus().getValue());

            entityManager.persist(task);
            return task;
        } catch (Exception e) {
            throw new RuntimeException("Task creation failed. " + e.getMessage(), e);
        }
    }

    @Transactional
    public Task update(TaskModel taskModel) {
        try {
            Task task = entityManager.find(Task.class, taskModel.getId().getValue());
            if (task == null) {
                throw new EmptyResultDataAccessException("Task not found", 1);
            }

            task.setTitle(taskModel.getTitle().getValue());
            task.setProjectId(taskModel.getProjectId().getValue());
            task.setPriorityId(taskModel.getPriority().getValue());
            task.setSprintId(taskModel.getSprintId() != null ? taskModel.getSprintId().getValue() : null);
            task.setDescription(taskModel.getDescription() != null ? taskModel.getDescription().getValue() : null);
            task.setProgress(taskModel.getProgress().getValue());
            task.setTenantId(taskModel.getTenantId());
            task.setDependsOnId(taskModel.getDependsOnId() != null ? taskModel.getDependsOnId().getValue() : null);
            task.setStartedAt(taskModel.getStartedAt() != null ? taskModel.getStartedAt().getValue().format("yyyy-MM-dd HH:mm:ss") : null);
            task.setDeadline(taskModel.getDeadline() != null ? taskModel.getDeadline().getValue().format("yyyy-MM-dd HH:mm:ss") : null);
            task.setCompletedAt(taskModel.getCompletedAt() != null ? taskModel.getCompletedAt().getValue().format("yyyy-MM-dd HH:mm:ss") : null);
            task.setRepeatDays(taskModel.getRepeatDays().getValue());
            task.setExplanationClipPath(taskModel.getExplanationClipPath() != null ? taskModel.getExplanationClipPath().getValue() : null);
            task.setMeetingCount(taskModel.getMeetingCount().getValue());
            task.setDuration(taskModel.getDuration().getValue());
            task.setStatusId(taskModel.getStatus() != null ? taskModel.getStatus().getValue() : null);

            entityManager.merge(task);
            return task;
        } catch (Exception e) {
            throw new RuntimeException("Task update failed. " + e.getMessage(), e);
        }
    }

    public List<Task> findByProjectId(ProjectId projectId) {
        try {
            Query query = entityManager.createQuery("SELECT t FROM Task t JOIN FETCH t.employees e JOIN FETCH t.status s WHERE t.projectId = :projectId");
            query.setParameter("projectId", projectId.getValue());
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Tasks retrieval failed. " + e.getMessage(), e);
        }
    }

    public Task findById(TaskId taskId) {
        try {
            Task task = entityManager.find(Task.class, taskId.getValue());
            if (task == null) {
                throw new EmptyResultDataAccessException("Task not found", 1);
            }
            return task;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the task. " + e.getMessage(), e);
        }
    }

    @Transactional
    public boolean delete(TaskId taskId) {
        try {
            Task task = entityManager.find(Task.class, taskId.getValue());
            if (task == null) {
                throw new EmptyResultDataAccessException("Task not found for deletion", 1);
            }
            entityManager.remove(task);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while deleting the task. " + e.getMessage(), e);
        }
    }

    public List<Task> getBacklogTasksByProject(ProjectId projectId) {
        try {
            Query query = entityManager.createQuery("SELECT t FROM Task t JOIN FETCH t.employees e JOIN FETCH t.status s WHERE t.projectId = :projectId AND t.sprintId IS NULL");
            query.setParameter("projectId", projectId.getValue());
            List<Task> tasks = query.getResultList();
            tasks.forEach(task -> {
                // Add emails to task as a property
                List<String> employeeEmails = task.getEmployees().stream()
                        .map(e -> e.getUser().getEmail())
                        .distinct()
                        .collect(Collectors.toList());
                task.setEmails(employeeEmails);
            });
            return tasks;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch backlog tasks. " + e.getMessage(), e);
        }
    }

    public List<Task> getTasksByProjectAndSprint(ProjectId projectId, SprintId sprintId) {
        try {
            Query query = entityManager.createQuery("SELECT t FROM Task t JOIN FETCH t.employees e JOIN FETCH t.status s WHERE t.projectId = :projectId AND t.sprintId = :sprintId");
            query.setParameter("projectId", projectId.getValue());
            query.setParameter("sprintId", sprintId.getValue());
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch tasks by project and sprint. " + e.getMessage(), e);
        }
    }

    public List<Task> findSubTasksByTaskId(TaskId taskId) {
        try {
            Task task = entityManager.find(Task.class, taskId.getValue());
            if (task == null) {
                throw new EmptyResultDataAccessException("Task not found", 1);
            }

            Query query = entityManager.createQuery("SELECT t FROM Task t JOIN FETCH t.employees e JOIN FETCH t.status s WHERE t.dependsOnId = :taskId");
            query.setParameter("taskId", taskId.getValue());
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch sub-tasks. " + e.getMessage(), e);
        }
    }

    public void assignTaskToEmployee(int employeeId, TaskId taskId) {
        try {
            Employee employee = entityManager.find(Employee.class, employeeId);
            if (employee == null) {
                throw new EmptyResultDataAccessException("Employee not found with ID: " + employeeId, 1);
            }

            Task task = entityManager.find(Task.class, taskId.getValue());
            if (task == null) {
                throw new EmptyResultDataAccessException("Task not found with ID: " + taskId.getValue(), 1);
            }

            employee.getTasks().add(task);
            entityManager.merge(employee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to assign task to employee: " + e.getMessage(), e);
        }
    }

    public void unassignTaskToEmployee(int employeeId, TaskId taskId) {
        try {
            Employee employee = entityManager.find(Employee.class, employeeId);
            if (employee == null) {
                throw new EmptyResultDataAccessException("Employee not found with ID: " + employeeId, 1);
            }

            Task task = entityManager.find(Task.class, taskId.getValue());
            if (task == null) {
                throw new EmptyResultDataAccessException("Task not found with ID: " + taskId.getValue(), 1);
            }

            employee.getTasks().remove(task);
            entityManager.merge(employee);
        } catch (Exception e) {
            throw new RuntimeException("Failed to unassign task from employee: " + e.getMessage(), e);
        }
    }

    // Additional methods for manhour calculations would be similarly adapted.
}
