package com.ecommerce.orderprocessor.controller;

import com.ecommerce.orderprocessor.dto.ResponseDTO;
import com.ecommerce.orderprocessor.dto.auth.AuthTokenDTO;
import com.ecommerce.orderprocessor.dto.auth.AuthenticationDTO;
import com.ecommerce.orderprocessor.dto.auth.RegistrationDTO;
import com.ecommerce.orderprocessor.service.UserService;
import com.ecommerce.orderprocessor.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService authService;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/registration")
    public ResponseEntity<ResponseDTO> authenticateAndGetToken(@RequestBody RegistrationDTO registrationDTO){
        ResponseDTO registrationResponse = authService.registerUser(registrationDTO);
        return ResponseUtil.wrapResponse(registrationResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthTokenDTO> login(@RequestBody AuthenticationDTO authenticationDTO){
        AuthTokenDTO token = authService.login(authenticationDTO);
        return ResponseEntity.ok(token);
    }

}
