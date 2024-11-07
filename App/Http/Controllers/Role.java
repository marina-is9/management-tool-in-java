package com.example.controller;

import com.example.dto.RoleDTO;
import com.example.factory.RoleFactory;
import com.example.model.Role;
import com.example.repository.RoleRepository;
import com.example.repository.PermissionsRepository;
import com.example.service.AuthManager;
import com.example.util.ResponseCode;
import com.example.util.ResponseHandler;
import com.example.util.PermissionId;
import com.example.util.RoleId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private AuthManager authManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionsRepository permissionsRepository;

    @GetMapping
    public ResponseEntity<?> index() {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Get all roles
            List<Role> roles = roleRepository.getAllRoles();

            // Return response with the roles
            return ResponseHandler.success(ResponseCode.SUCCESS, roles);
        } catch (IllegalArgumentException e) {
            // Return invalid input response
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, ResponseCode.BAD_REQUEST.getMessage(), e.getMessage(), null);
        } catch (Exception e) {
            // Return general exception response
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage(), null);
        }
    }

    @PostMapping
    public ResponseEntity<?> store(@RequestBody RoleDTO roleDTO) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Create Role model based on DTO
            Role roleModel = RoleFactory.create(roleDTO);

            // Save Role to database
            Role role = roleRepository.save(roleModel);

            // Return response with the created role data
            return ResponseHandler.success(ResponseCode.SUCCESS, role);
        } catch (IllegalArgumentException e) {
            // Return invalid input response
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, ResponseCode.BAD_REQUEST.getMessage(), e.getMessage(), null);
        } catch (Exception e) {
            // Return general exception response
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage(), null);
        }
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<?> viewRole(@PathVariable int roleId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Create RoleId object
            RoleId roleIdObj = new RoleId(roleId);

            // Fetch role from repository
            Role role = roleRepository.getById(roleIdObj);

            // Return response with the role
            return ResponseHandler.success(ResponseCode.SUCCESS, role);
        } catch (IllegalArgumentException e) {
            // Return invalid input response
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, ResponseCode.BAD_REQUEST.getMessage(), e.getMessage(), null);
        } catch (Exception e) {
            // Return general exception response
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage(), null);
        }
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<?> updateRole(@RequestBody RoleDTO roleDTO, @PathVariable int roleId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Add roleId to the DTO
            roleDTO.setId(roleId);

            // Create Role model based on DTO
            Role roleModel = RoleFactory.create(roleDTO);

            // Save updated Role to database
            Role updatedRole = roleRepository.save(roleModel);

            // Return response with the updated role data
            return ResponseHandler.success(ResponseCode.SUCCESS, updatedRole);
        } catch (IllegalArgumentException e) {
            // Return invalid input response
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, ResponseCode.BAD_REQUEST.getMessage(), e.getMessage(), null);
        } catch (Exception e) {
            // Return general exception response
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage(), null);
        }
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable int roleId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Create RoleId object
            RoleId roleIdObj = new RoleId(roleId);

            // Delete role by ID
            roleRepository.deleteById(roleIdObj);

            // Return success response
            return ResponseHandler.success(ResponseCode.SUCCESS);
        } catch (IllegalArgumentException e) {
            // Return invalid input response
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, ResponseCode.BAD_REQUEST.getMessage(), e.getMessage(), null);
        } catch (Exception e) {
            // Return general exception response
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage(), null);
        }
    }

    @PostMapping("/{roleId}/permissions")
    public ResponseEntity<?> assignPermissions(@RequestBody PermissionId permissionId, @PathVariable int roleId) {
        try {
            // Authenticate user
            authManager.authenticateUser();

            // Create RoleId object
            RoleId roleIdObj = new RoleId(roleId);

            // Fetch role by ID
            Role role = roleRepository.getById(roleIdObj);

            // Fetch permission by ID
            Role permission = permissionsRepository.getById(permissionId);

            // Assign permissions to role
            Role updatedRole = roleRepository.assignPermissions(role, permission);

            // Return successful response
            return ResponseHandler.success(ResponseCode.SUCCESS, updatedRole);
        } catch (IllegalArgumentException e) {
            // Return invalid input response
            return ResponseHandler.error(ResponseCode.BAD_REQUEST, ResponseCode.BAD_REQUEST.getMessage(), e.getMessage(), null);
        } catch (Exception e) {
            // Return general exception response
            return ResponseHandler.error(ResponseCode.INTERNAL_SERVER_ERROR, ResponseCode.INTERNAL_SERVER_ERROR.getMessage(), e.getMessage(), null);
        }
    }
}
