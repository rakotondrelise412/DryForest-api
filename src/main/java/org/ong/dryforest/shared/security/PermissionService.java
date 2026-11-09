
package org.ong.dryforest.shared.security;

import org.ong.dryforest.enums.Permission;
import org.ong.dryforest.enums.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("permissionService")
public class PermissionService {

    @SuppressWarnings("unused")
    public boolean hasPermission(Permission permission) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> {
                    try {
                        Role role = Role.valueOf(authority.getAuthority());

                        return RolePermissions.hasPermission(
                                role,
                                permission
                        );

                    } catch (IllegalArgumentException exception) {
                        return false;
                    }
                });
    }
}
