package com.ecommerce.orderprocessor.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserPermissionDTO {

    private Long entityId;

    private String entityName;

    private List<UserPermissionActionDTO> entityActions;

    private Long roleId;
}
