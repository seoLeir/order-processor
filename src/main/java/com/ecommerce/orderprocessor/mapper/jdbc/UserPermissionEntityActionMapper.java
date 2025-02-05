package com.ecommerce.orderprocessor.mapper.jdbc;

import com.ecommerce.orderprocessor.dto.user.UserPermissionActionDTO;
import com.ecommerce.orderprocessor.util.JdbcUtils;
import lombok.SneakyThrows;

import java.sql.ResultSet;

public final class UserPermissionEntityActionMapper {

    @SneakyThrows
    public static UserPermissionActionDTO permissionActionListMapper(ResultSet rs, Integer rowNum) {
        UserPermissionActionDTO dto = new UserPermissionActionDTO();

        dto.setEntityId(JdbcUtils.getValue(Long.class, "permission_entity_id", rs));
        dto.setEntityName(JdbcUtils.getValue(String.class, "permission_entity_name", rs));
        dto.setRoleId(JdbcUtils.getValue(Long.class, "role_id", rs));
        dto.setId(JdbcUtils.getValue(Long.class, "permission_action_id", rs));
        dto.setAction(JdbcUtils.getValue(String.class, "permission_action_name", rs));
        dto.setActionDescription(JdbcUtils.getValue(String.class, "permission_action_description", rs));
        dto.setEntityActionRoleName(JdbcUtils.getValue(String.class, "permission_action_description", rs));

        return dto;
    }

}
