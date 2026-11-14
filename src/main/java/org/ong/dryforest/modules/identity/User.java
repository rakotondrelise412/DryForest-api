
package org.ong.dryforest.modules.identity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.ong.dryforest.enums.Role;
import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.shared.base.AbstractEntity;
import org.ong.dryforest.shared.security.RolePermissions;
import org.ong.dryforest.shared.sort.SortableField;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractEntity implements UserDetails {

    @SortableField(field = "createdAt", initialize = true)
    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Long salary;

    @OneToOne
    @JoinColumn(nullable = false)
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Objects.requireNonNull(role, "Le rôle de l'utilisateur ne peut pas être null");

        Set<GrantedAuthority> authorities = new HashSet<>();

        // Autorité du rôle
        authorities.add(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );

        // Permissions associées au rôle
        RolePermissions.getPermissions(role)
                .forEach(permission ->
                        authorities.add(
                                new SimpleGrantedAuthority(permission.name())
                        )
                );

        return Set.copyOf(authorities);
    }
}
