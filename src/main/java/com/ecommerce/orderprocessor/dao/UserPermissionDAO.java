package com.ecommerce.orderprocessor.dao;

import com.ecommerce.orderprocessor.dto.user.UserPermissionActionDTO;
import com.ecommerce.orderprocessor.model.RoleEnum;

import java.util.List;

public interface UserPermissionDAO {

    List<UserPermissionActionDTO> getUserPermissionEntityActionByRole(RoleEnum role);
}
