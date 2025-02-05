package com.ecommerce.orderprocessor.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "permission_entity_role_rules")
@Entity
public class PermissionEntityRoleRule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_entity_role_rules_id_seq")
    @SequenceGenerator(name = "permission_entity_role_rules_id_seq", sequenceName = "permission_entity_role_rules_id_seq", allocationSize = 1)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "permission_entity", nullable = false)
    private PermissionEntity permissionEntity;

    @ManyToOne
    @JoinColumn(name = "permission", nullable = false)
    private Permission permission;

    @Column(name = "description")
    private String description;

    @Column(name = "system_name")
    private String systemName;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionEntityRoleRule that = (PermissionEntityRoleRule) o;
        return Objects.equals(systemName, that.systemName) && Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemName, role);
    }
}
