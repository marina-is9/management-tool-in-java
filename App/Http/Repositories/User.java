package com.example.repositories;

import com.example.models.User;
import com.example.models.Role;
import com.example.models.Tenant;
import com.example.models.UserId;
import com.example.models.RoleId;
import com.example.services.UserService;
import com.example.services.RoleService;
import com.example.exceptions.ModelNotFoundException;
import com.example.exceptions.GeneralException;
import java.util.List;

public class UserRepository {

    private final UserService userService;
    private final RoleService roleService;

    public UserRepository(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    public User store(User user) throws GeneralException {
        try {
            // Prepare user data
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setTenantId(user.getTenantId());

            return userService.createUser(newUser);
        } catch (Exception e) {
            throw new GeneralException("User creation failed. " + e.getMessage(), e);
        }
    }

    public boolean assignRole(UserId userId, RoleId roleId) throws GeneralException {
        try {
            // Fetch user and role by ID
            User user = userService.getUserById(userId);
            Role role = roleService.getRoleById(roleId);

            if (user == null || role == null) {
                return false; // User or Role not found
            }

            // Assign role to user
            user.setRole(role);
            userService.updateUser(user);

            return true; // Successfully assigned role
        } catch (Exception e) {
            // Log error and return failure
            throw new GeneralException("Error assigning role: " + e.getMessage(), e);
        }
    }

    public List<User> getAllUsers() throws GeneralException {
        try {
            return userService.getAllUsers();
        } catch (Exception e) {
            throw new GeneralException("Error fetching users by tenant. " + e.getMessage(), e);
        }
    }

    public User getById(UserId userId) throws ModelNotFoundException {
        try {
            return userService.getUserById(userId);
        } catch (Exception e) {
            throw new ModelNotFoundException("User retrieval failed. " + e.getMessage(), e);
        }
    }

    public User update(User user) throws GeneralException {
        try {
            User existingUser = userService.getUserById(user.getId());

            if (existingUser == null) {
                throw new ModelNotFoundException("User not found for update.");
            }

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());

            return userService.updateUser(existingUser);
        } catch (Exception e) {
            throw new GeneralException("User update failed. " + e.getMessage(), e);
        }
    }

    public boolean deleteById(UserId userId) throws GeneralException {
        try {
            User user = userService.getUserById(userId);

            if (user == null) {
                throw new ModelNotFoundException("User not found for deletion.");
            }

            return userService.deleteUser(user);
        } catch (Exception e) {
            throw new GeneralException("User deletion failed. " + e.getMessage(), e);
        }
    }

    public User createWithoutPassword(UserDTO userDTO) throws GeneralException {
        try {
            User newUser = new User();
            newUser.setName(userDTO.getName());
            newUser.setEmail(userDTO.getEmail());
            newUser.setTenantId(userDTO.getTenantId());
            newUser.setRoleId(userDTO.getRoleId() != null ? userDTO.getRoleId() : 3); // Default role ID is 3

            // Set password if provided
            if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
                newUser.setPassword(userDTO.getPassword());
            }

            return userService.createUser(newUser);
        } catch (Exception e) {
            throw new GeneralException("Failed to create user without password. " + e.getMessage(), e);
        }
    }
}
