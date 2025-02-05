package com.ecommerce.orderprocessor.service;

import com.ecommerce.orderprocessor.exception.BaseApplicationException;
import com.ecommerce.orderprocessor.model.User;
import com.ecommerce.orderprocessor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component("userDetailsService")
@RequiredArgsConstructor
public class DomainUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Authenticating user: {}", username);

        Optional<User> optionalUser = userRepository.findUserByLogin(username);

        return optionalUser.map(user -> createSpringSecurityUser(username, user))
                .orElseThrow(() -> new ApplicationContextException("User not found: " + username));
    }

    private org.springframework.security.core.userdetails.User createSpringSecurityUser(String lowercaseLogin, User user) {
        if (!user.getActivated() || user.getDeleted()) {
            throw new BaseApplicationException("User " + lowercaseLogin + " was not activated", HttpStatus.UNAUTHORIZED);
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getSystemName()))
                .collect(Collectors.toList());
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getSystemName()));

        log.info("USER - {} AUTHORITIES - {}", user.getLogin(), grantedAuthorities);
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthorities);
    }
}
