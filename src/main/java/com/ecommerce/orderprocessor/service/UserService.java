package com.ecommerce.orderprocessor.service;

import com.ecommerce.orderprocessor.dto.ResponseDTO;
import com.ecommerce.orderprocessor.dto.auth.AuthTokenDTO;
import com.ecommerce.orderprocessor.dto.auth.AuthenticationDTO;
import com.ecommerce.orderprocessor.dto.auth.RegistrationDTO;
import com.ecommerce.orderprocessor.dto.user.UserDTO;
import com.ecommerce.orderprocessor.exception.BaseApplicationException;
import com.ecommerce.orderprocessor.mapper.dto.UserMapper;
import com.ecommerce.orderprocessor.model.Authority;
import com.ecommerce.orderprocessor.model.RoleEnum;
import com.ecommerce.orderprocessor.model.User;
import com.ecommerce.orderprocessor.repository.AuthorityRepository;
import com.ecommerce.orderprocessor.repository.RoleRepository;
import com.ecommerce.orderprocessor.repository.UserRepository;
import com.ecommerce.orderprocessor.security.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final AuthorityRepository authorityRepository;

    private final JwtTokenUtils jwtTokenUtils;

    private final CacheManager cacheManager;

    private final PasswordEncoder passwordEncoder;

    private final DomainUserDetailsService userDetailsService;


    public ResponseDTO registerUser(RegistrationDTO userDTO) {
        if (userRepository.findUserByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            return new ResponseDTO(false, "User with this login already exists", HttpStatus.CONFLICT);
        } else {
            User user = new User();
            user.setLogin(userDTO.getLogin().toLowerCase());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setImageUrl(userDTO.getImageUrl());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setActivated(true);
            user.setDeleted(false);
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setCreatedBy("SYSTEM");

            roleRepository.findById(RoleEnum.USER.getValue()).ifPresent(user::setRole);

            if (userDTO.getPermissions() != null) {
                Set<Authority> permissions = userDTO.getPermissions().stream()
                        .map(authorityRepository::findBySystemName)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
                user.setAuthorities(permissions);
            }

            userRepository.save(user);
            this.clearUserCaches(user);
            log.debug("Created Information for User: {}", user);

            return new ResponseDTO(true, "Success", HttpStatus.CREATED);
        }
    }

    public AuthTokenDTO login(AuthenticationDTO authenticationDTO) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationDTO.getLogin());

        System.out.println(passwordEncoder.encode(authenticationDTO.getPassword()));
        System.out.println(userDetails.getPassword());

        if (passwordEncoder.matches(userDetails.getPassword(), authenticationDTO.getPassword())) {
            return new AuthTokenDTO(jwtTokenUtils.generateToken(userDetails.getUsername(), userDetails.getAuthorities()));
        } else {
            throw new BaseApplicationException("Username or password are incorrect", HttpStatus.UNAUTHORIZED);
        }
    }

    private void clearUserCaches(User user) {
        Objects.requireNonNull(cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE))
                .evict(user.getLogin());
    }

    public Page<UserDTO> getUsersPageList(Long roleId, Pageable pageable) {
        return userRepository.findAllByRole(roleId, pageable)
                .map(UserMapper::userDtoFromUserModel);
    }

    public UserDTO getCurrentUserProfile(String login) {
        return userRepository.findUserByLogin(login)
                .map(UserMapper::userDtoFromUserModel)
                .orElseThrow(() -> new BaseApplicationException("User not found", HttpStatus.NOT_FOUND));
    }

    public ResponseDTO createUser(UserDTO userDTO, String login) {
        if (userRepository.findOneByLogin(userDTO.getLogin().toLowerCase()).isPresent()) {
            return new ResponseDTO(false, "User with this login already exists", HttpStatus.CONFLICT);
        } else {
            User user = new User();
            user.setLogin(userDTO.getLogin().toLowerCase());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(true);
            user.setDeleted(false);
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setCreatedBy(login);

            roleRepository.findById(RoleEnum.USER.getValue()).ifPresent(user::setRole);

            if (userDTO.getAuthorities() != null) {
                Set<Authority> permissions = userDTO.getAuthorities().stream()
                        .map(authorityRepository::findBySystemName)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toSet());
                user.setAuthorities(permissions);
            }

            userRepository.save(user);
            this.clearUserCaches(user);
            log.debug("Created Information for User: {}", user);

            return new ResponseDTO(true, "Success", HttpStatus.CREATED);
        }
    }

    public ResponseDTO updateUser(UserDTO userDTO, String currentUserLogin) {
        Optional<User> optionalUser = userRepository.findUserByLogin(userDTO.getLogin());

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            this.clearUserCaches(user);

            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.getActivated());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setUpdatedBy(currentUserLogin);
            user.setUpdatedAt(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            Set<Authority> managedAuthorities = user.getAuthorities();

            managedAuthorities.clear();
            userDTO.getAuthorities().stream()
                    .map(authorityRepository::findBySystemName)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .forEach(managedAuthorities::add);

            userRepository.save(user);
            this.clearUserCaches(user);
            log.debug("Updated user: {}", user);

            return new ResponseDTO(true, "Success", HttpStatus.OK);
        } else {
            return new ResponseDTO(false, "User not found", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseDTO deleteUser(Long userId, String userLogin) {
        Optional<User> optionalUser = userRepository.findById(userId);

        return optionalUser.map(user -> {
                    this.clearUserCaches(user);

                    user.setDeleted(true);
                    user.setDeletedBy(userLogin);
                    user.setDeletedAt(LocalDateTime.now());

                    userRepository.save(user);
                    this.clearUserCaches(user);

                    log.debug("Deleted user: {}", user);
                    return new ResponseDTO(true, "Success", HttpStatus.OK);
                })
                .orElseThrow(() -> new BaseApplicationException("User not found", HttpStatus.NOT_FOUND));
    }
}
