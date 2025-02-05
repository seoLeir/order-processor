package com.ecommerce.orderprocessor.service.impl;

import com.ecommerce.orderprocessor.dao.UserPermissionDAO;
import com.ecommerce.orderprocessor.dto.role.RoleHelperDTO;
import com.ecommerce.orderprocessor.dto.user.UserPermissionActionDTO;
import com.ecommerce.orderprocessor.dto.user.UserPermissionDTO;
import com.ecommerce.orderprocessor.model.RoleEnum;
import com.ecommerce.orderprocessor.repository.RoleRepository;
import com.ecommerce.orderprocessor.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.List;

import static java.util.stream.Collectors.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final UserPermissionDAO userPermissionDAO;

    @Override
    public List<RoleHelperDTO> getRolesHelper() {
        return roleRepository.findAllByOrderByCreatedAtAsc()
                .stream()
                .map(role -> {
                    RoleHelperDTO helperDTO = new RoleHelperDTO();

                    helperDTO.setId(role.getId());
                    helperDTO.setRoleName(role.getSystemName());

                    return helperDTO;
                })
                .collect(toList());
    }

    @Override
    public List<UserPermissionDTO> getUserPermissionEntityActionByRoleId(Long roleId) {
        RoleEnum role = RoleEnum.valueDeserializer(roleId);

        return userPermissionDAO.getUserPermissionEntityActionByRole(role)
                .stream()
                .collect(groupingBy(
                        action -> new AbstractMap.SimpleEntry<>(action.getEntityId(), action.getRoleId())
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    Long entityId = entry.getKey().getKey();
                    Long eRoleId = entry.getKey().getValue();
                    List<UserPermissionActionDTO> entityActions = entry.getValue();

                    UserPermissionDTO dto = new UserPermissionDTO();
                    dto.setEntityId(entityId);
                    dto.setRoleId(eRoleId);
                    dto.setEntityName(entityActions.get(0).getEntityName());
                    dto.setEntityActions(entityActions);

                    return dto;
                })
                .collect(toList());
    }
}
