package com.example.policy;

import com.example.model.Task;
import com.example.model.User;
import org.springframework.stereotype.Component;

@Component
public class TaskPolicy {

    public boolean view(User user, Task task) {
        return user.getTenantId().equals(task.getTenantId());
    }

    public boolean create(User user) {
        return false;
    }

    public boolean update(User user, Task task) {
        return user.getTenantId().equals(task.getTenantId()) && 
               (user.getRole().equals("Admin") || user.getRole().equals("Manager"));
    }

    public boolean delete(User user) {
        return false;
    }
}
