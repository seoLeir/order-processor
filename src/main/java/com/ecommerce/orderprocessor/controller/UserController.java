package com.ecommerce.orderprocessor.controller;

import com.ecommerce.orderprocessor.dto.ResponseDTO;
import com.ecommerce.orderprocessor.dto.user.UserDTO;
import com.ecommerce.orderprocessor.service.UserService;
import com.ecommerce.orderprocessor.util.PaginationUtil;
import com.ecommerce.orderprocessor.util.ResponseUtil;
import com.ecommerce.orderprocessor.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/pageList")
    public ResponseEntity<List<UserDTO>> getUsersPageList(@RequestParam("roleId") Long roleId, Pageable pageable) {
        Page<UserDTO> page = userService.getUsersPageList(roleId, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok()
                .headers(headers)
                .body(page.getContent());
    }

    // User current profile
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getCurrentUserProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile("admin"));
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> createUser(@RequestBody UserDTO userDTO) {
        return ResponseUtil.wrapResponse(userService.createUser(userDTO, SecurityUtils.getCurrentUserLogin()));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateUser(@RequestBody UserDTO userDTO) {
        return ResponseUtil.wrapResponse(userService.updateUser(userDTO, SecurityUtils.getCurrentUserLogin()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable("id") Long userId) {
        return ResponseUtil.wrapResponse(userService.deleteUser(userId, SecurityUtils.getCurrentUserLogin()));
    }
}
