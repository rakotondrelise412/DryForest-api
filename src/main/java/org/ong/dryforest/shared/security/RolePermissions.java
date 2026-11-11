
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

            // ADMIN : tous les droits
            case ADMIN -> EnumSet.allOf(Permission.class);

            // MANAGER : gestion métier complète,
            // sauf utilisateurs, personnes et sites.
            case MANAGER -> EnumSet.of(

                    // Sites et personnes : consultation uniquement
                    Permission.SITE_READ,
                    Permission.PERSON_READ,

                    // Statistiques
                    Permission.GLOBAL_STATISTICS_READ,
                    Permission.SITE_STATISTICS_READ,

                    // Zones
                    Permission.ZONE_READ,
                    Permission.ZONE_CREATE,
                    Permission.ZONE_UPDATE,
                    Permission.ZONE_DELETE,

                    // Plantations
                    Permission.PLANTATION_READ,
                    Permission.PLANTATION_CREATE,
                    Permission.PLANTATION_UPDATE,
                    Permission.PLANTATION_DELETE,

                    // Incidents
                    Permission.INCIDENT_READ,
                    Permission.INCIDENT_CREATE,
                    Permission.INCIDENT_UPDATE,
                    Permission.INCIDENT_DELETE,

                    // Patrouilles
                    Permission.PATROL_READ,
                    Permission.PATROL_CREATE,
                    Permission.PATROL_UPDATE,
                    Permission.PATROL_DELETE,

                    // Animaux
                    Permission.ANIMAL_READ,
                    Permission.ANIMAL_CREATE,
                    Permission.ANIMAL_UPDATE,
                    Permission.ANIMAL_DELETE,

                    // Observations
                    Permission.OBSERVATION_READ,
                    Permission.OBSERVATION_CREATE,
                    Permission.OBSERVATION_UPDATE,
                    Permission.OBSERVATION_DELETE,

                    // Suivi des animaux
                    Permission.TRACKING_READ,
                    Permission.TRACKING_CREATE,
                    Permission.TRACKING_UPDATE,
                    Permission.TRACKING_DELETE,

                    // Données de référence
                    Permission.REFERENCE_READ,
                    Permission.REFERENCE_CREATE,
                    Permission.REFERENCE_UPDATE,
                    Permission.REFERENCE_DELETE,

                    // Synchronisation
                    Permission.SYNC_PUSH,
                    Permission.SYNC_PULL
            );

            // PATROL_AGENT : opérations de terrain
            case PATROL_AGENT -> EnumSet.of(

                    // Statistiques
                    Permission.GLOBAL_STATISTICS_READ,
                    Permission.SITE_STATISTICS_READ,

                    // Consultation inter-sites
                    Permission.SITE_READ,
                    Permission.ZONE_READ,
                    Permission.PLANTATION_READ,
                    Permission.INCIDENT_READ,
                    Permission.PATROL_READ,
                    Permission.ANIMAL_READ,
                    Permission.OBSERVATION_READ,
                    Permission.TRACKING_READ,
                    Permission.REFERENCE_READ,

                    // Incidents : création et modification
                    Permission.INCIDENT_CREATE,
                    Permission.INCIDENT_UPDATE,

                    // Patrouilles : création et modification
                    Permission.PATROL_CREATE,
                    Permission.PATROL_UPDATE,

                    // Observations : création et modification
                    Permission.OBSERVATION_CREATE,
                    Permission.OBSERVATION_UPDATE,

                    // Suivi des animaux : création et modification
                    Permission.TRACKING_CREATE,
                    Permission.TRACKING_UPDATE,

                    // Synchronisation
                    Permission.SYNC_PUSH,
                    Permission.SYNC_PULL
            );

            // OBSERVER : consultation uniquement
            case OBSERVER -> EnumSet.of(

                    // Statistiques
                    Permission.GLOBAL_STATISTICS_READ,
                    Permission.SITE_STATISTICS_READ,

                    // Consultation inter-sites
                    Permission.SITE_READ,
                    Permission.ZONE_READ,
                    Permission.PLANTATION_READ,
                    Permission.INCIDENT_READ,
                    Permission.PATROL_READ,
                    Permission.ANIMAL_READ,
                    Permission.OBSERVATION_READ,
                    Permission.TRACKING_READ,
                    Permission.REFERENCE_READ,

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
