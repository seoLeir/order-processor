package com.ecommerce.orderprocessor.controller;

import com.ecommerce.orderprocessor.dto.role.RoleHelperDTO;
import com.ecommerce.orderprocessor.dto.user.UserPermissionDTO;
import com.ecommerce.orderprocessor.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/helper")
    public List<RoleHelperDTO> getRoleHelper() {
        return roleService.getRolesHelper();
    }


    @GetMapping("/permissions/helper")
    public List<UserPermissionDTO> getUserPermissionEntityActionByRoleId(@RequestParam("roleId") Long roleId) {
        return roleService.getUserPermissionEntityActionByRoleId(roleId);
    }
}
