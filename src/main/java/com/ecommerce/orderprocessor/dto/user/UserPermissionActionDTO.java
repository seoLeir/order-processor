package com.ecommerce.orderprocessor.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserPermissionActionDTO {

    @JsonIgnore
    private Long entityId;

    @JsonIgnore
    private String entityName;

    @JsonIgnore
    private Long roleId;

    private Long id;

    // CREATE, UPDATE, DELETE, VIEW
    private String action;


    // Создать, обновить, удалить, просмотреть
    private String actionDescription;

    // Соответствующая роль

    private String entityActionRoleName;
}
