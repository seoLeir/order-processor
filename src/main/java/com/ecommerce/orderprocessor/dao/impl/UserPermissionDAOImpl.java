package com.ecommerce.orderprocessor.dao.impl;

import com.ecommerce.orderprocessor.dao.UserPermissionDAO;
import com.ecommerce.orderprocessor.dto.user.UserPermissionActionDTO;
import com.ecommerce.orderprocessor.mapper.jdbc.UserPermissionEntityActionMapper;
import com.ecommerce.orderprocessor.model.RoleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserPermissionDAOImpl implements UserPermissionDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<UserPermissionActionDTO> getUserPermissionEntityActionByRole(RoleEnum role) {
        String sql = """
                SELECT
                  pe.id AS permission_entity_id,
                  pe.name AS permission_entity_name,
                  perr.role_id AS role_id,
                  perr.id AS permission_action_id,
                  p.system_name AS permission_action_name,
                  perr.permission AS permission_action_description,
                  perr.system_name AS permission_action_role_name
                FROM permission_entity_role_rules perr
                JOIN permission_entities pe on perr.permission_entity = pe.id
                JOIN permissions p on perr.permission = p.id
                WHERE perr.role_id = ?
                """;

        return jdbcTemplate.query(sql, UserPermissionEntityActionMapper::permissionActionListMapper, role.getValue());
    }
}
