package com.ecommerce.orderprocessor.dto.user;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    private Long id;

    private String login;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String email;

    private String imageUrl;

    private Boolean activated;

    private String password;

    private Long role;

    private Set<String> authorities;
}
