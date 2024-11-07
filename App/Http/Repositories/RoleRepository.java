package com.example.repositories;

import com.example.models.Role;
import com.example.models.RoleId;
import com.example.exceptions.ModelNotFoundException;
import com.example.exceptions.GeneralException;
import com.example.services.RoleService;
import java.util.List;

public class RoleRepository {

    private final RoleService roleService;

    public RoleRepository(RoleService roleService) {
        this.roleService = roleService;
    }

    public List<Role> getAllRoles() throws GeneralException {
        try {
            return roleService.getAllRoles();
        } catch (Exception e) {
            throw new GeneralException("Failed to retrieve roles. " + e.getMessage(), e);
        }
    }

    public Role createRole(Role roleModel) throws GeneralException {
        try {
            Role role = new Role();
            role.setName(roleModel.getName());
            role.setTenantId(roleModel.getTenantId());
            return roleService.createRole(role);
        } catch (Exception e) {
            throw new GeneralException("Role creation failed. " + e.getMessage(), e);
        }
    }

    public Role getById(RoleId roleId) throws ModelNotFoundException, GeneralException {
        try {
            return roleService.getRoleById(roleId);
        } catch (ModelNotFoundException e) {
            throw new ModelNotFoundException("Role not found. " + e.getMessage(), e);
        } catch (Exception e) {
            throw new GeneralException("An error occurred while retrieving the role. " + e.getMessage(), e);
        }
    }

    public Role update(Role roleModel) throws GeneralException {
        try {
            Role existingRole = roleService.getRoleById(roleModel.getId());
            existingRole.setName(roleModel.getName());
            existingRole.setTenantId(roleModel.getTenantId());
            return roleService.updateRole(existingRole);
        } catch (ModelNotFoundException e) {
            throw new ModelNotFoundException("Role update failed. " + e.getMessage(), e);
        } catch (Exception e) {
            throw new GeneralException("Role update failed. " + e.getMessage(), e);
        }
    }

    public Role deleteById(RoleId roleId) throws GeneralException {
        try {
            Role role = roleService.getRoleById(roleId);
            roleService.deleteRole(role);
            return role;
        } catch (Exception e) {
            throw new GeneralException("Role deletion failed. " + e.getMessage(), e);
        }
    }

    public Role assignPermissions(Role role, List<Integer> permissionIds) throws GeneralException {
        try {
            roleService.assignPermissions(role, permissionIds);
            return role;
        } catch (Exception e) {
            throw new GeneralException("Failed to assign permissions. " + e.getMessage(), e);
        }
    }
}
