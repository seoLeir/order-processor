package com.ecommerce.orderprocessor.dto.auth;

import lombok.Data;

import java.util.Set;

@Data
public class RegistrationDTO {

    private String login;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String email;

    private String imageUrl;

    private String password;

    private Long roleId;

    private Set<String> permissions;

}
