package com.ecommerce.orderprocessor.repository;

import com.ecommerce.orderprocessor.model.PermissionEntityRoleRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionEntityRoleRuleRepository extends JpaRepository<PermissionEntityRoleRule, Long> {

    Optional<PermissionEntityRoleRule> findBySystemName(String systemName);

}
