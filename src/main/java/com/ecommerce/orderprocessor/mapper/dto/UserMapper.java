package com.ecommerce.orderprocessor.mapper.dto;

import com.ecommerce.orderprocessor.dto.user.UserDTO;
import com.ecommerce.orderprocessor.model.Authority;
import com.ecommerce.orderprocessor.model.User;

import static java.util.stream.Collectors.toSet;

public class UserMapper {
    public static UserDTO userDtoFromUserModel(User user) {
        UserDTO dto = new UserDTO();

        dto.setId(user.getId());
        dto.setLogin(user.getLogin());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setImageUrl(user.getImageUrl());
        dto.setActivated(user.getActivated());
        dto.setRole(user.getRole().getId());
        dto.setAuthorities(user.getAuthorities().stream()
                .map(Authority::getSystemName)
                .collect(toSet())
        );

        return dto;
    }
}
