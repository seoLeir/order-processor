package com.ecommerce.orderprocessor.service;

import com.ecommerce.orderprocessor.dto.role.RoleHelperDTO;
import com.ecommerce.orderprocessor.dto.user.UserPermissionDTO;

import java.util.List;

public interface RoleService {

    List<RoleHelperDTO> getRolesHelper();

    List<UserPermissionDTO> getUserPermissionEntityActionByRoleId(Long roleId);
}
