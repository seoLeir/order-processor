package com.ecommerce.orderprocessor.repository;

import com.ecommerce.orderprocessor.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findBySystemName(String systemName);
}
