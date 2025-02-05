package com.ecommerce.orderprocessor.repository;

import com.ecommerce.orderprocessor.model.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    String USERS_BY_LOGIN_CACHE = "usersByLogin";

    Optional<User> findOneByLogin(String login);

    @EntityGraph(attributePaths = {"authorities", "role"})
    @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
    Optional<User> findUserByLogin(String login);

    @Query("FROM User WHERE role.id = :roleId AND deleted IS FALSE")
    Page<User> findAllByRole(@Param("roleId") Long roleId, Pageable pageable);

}
