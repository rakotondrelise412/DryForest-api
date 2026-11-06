
package org.ong.dryforest.shared.security;

import java.util.EnumSet;
import java.util.Set;

import org.ong.dryforest.enums.Permission;
import org.ong.dryforest.enums.Role;

public final class RolePermissions {

    private RolePermissions() {
    }

    public static Set<Permission> getPermissions(Role role) {

        return switch (role) {

            case ADMIN -> EnumSet.allOf(Permission.class);

            case MANAGER -> EnumSet.of(

                    // Consultation globale
                    Permission.GLOBAL_STATISTICS_READ,
                    Permission.SITE_STATISTICS_READ,

                    // Consultation des données
                    Permission.SITE_READ,
                    Permission.ZONE_READ,
                    Permission.PLANTATION_READ,
                    Permission.INCIDENT_READ,
                    Permission.PATROL_READ,

                    // Gestion opérationnelle
                    Permission.ZONE_CREATE,
                    Permission.ZONE_UPDATE,
                    Permission.ZONE_DELETE,

                    Permission.PLANTATION_CREATE,
                    Permission.PLANTATION_UPDATE,
                    Permission.PLANTATION_DELETE,

                    Permission.INCIDENT_CREATE,
                    Permission.INCIDENT_UPDATE,
                    Permission.INCIDENT_DELETE,

                    Permission.PATROL_CREATE,
                    Permission.PATROL_UPDATE,
                    Permission.PATROL_DELETE,

                    // Synchronisation
                    Permission.SYNC_PUSH,
                    Permission.SYNC_PULL
            );

            case PATROL_AGENT -> EnumSet.of(

                    // Consultation globale
                    Permission.GLOBAL_STATISTICS_READ,
                    Permission.SITE_STATISTICS_READ,

                    // Consultation
                    Permission.SITE_READ,
                    Permission.ZONE_READ,
                    Permission.PLANTATION_READ,
                    Permission.INCIDENT_READ,
                    Permission.PATROL_READ,

                    // Opérations de terrain
                    Permission.INCIDENT_CREATE,
                    Permission.INCIDENT_UPDATE,

                    Permission.PATROL_CREATE,
                    Permission.PATROL_UPDATE,

                    // Synchronisation
                    Permission.SYNC_PUSH,
                    Permission.SYNC_PULL
            );

            case OBSERVER -> EnumSet.of(

                    // Consultation globale
                    Permission.GLOBAL_STATISTICS_READ,
                    Permission.SITE_STATISTICS_READ,

                    // Consultation uniquement
                    Permission.SITE_READ,
                    Permission.ZONE_READ,
                    Permission.PLANTATION_READ,
                    Permission.INCIDENT_READ,
                    Permission.PATROL_READ,

                    // Synchronisation en lecture
                    Permission.SYNC_PULL
            );
        };
    }

    public static boolean hasPermission(
            Role role,
            Permission permission
    ) {

        return getPermissions(role).contains(permission);
    }
}
