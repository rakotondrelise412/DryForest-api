
package org.ong.dryforest.enums;

public enum Permission {

    // Gestion des utilisateurs
    USER_READ,
    USER_CREATE,
    USER_UPDATE,
    USER_DELETE,

    // Gestion des personnes
    PERSON_READ,
    PERSON_CREATE,
    PERSON_UPDATE,
    PERSON_DELETE,

    // Gestion des sites
    SITE_READ,
    SITE_CREATE,
    SITE_UPDATE,
    SITE_DELETE,

    // Gestion des zones
    ZONE_READ,
    ZONE_CREATE,
    ZONE_UPDATE,
    ZONE_DELETE,

    // Gestion des plantations
    PLANTATION_READ,
    PLANTATION_CREATE,
    PLANTATION_UPDATE,
    PLANTATION_DELETE,

    // Gestion des incidents
    INCIDENT_READ,
    INCIDENT_CREATE,
    INCIDENT_UPDATE,
    INCIDENT_DELETE,

    // Gestion des patrouilles
    PATROL_READ,
    PATROL_CREATE,
    PATROL_UPDATE,
    PATROL_DELETE,

    // Statistiques
    GLOBAL_STATISTICS_READ,
    SITE_STATISTICS_READ,

    // Synchronisation mobile
    SYNC_PUSH,
    SYNC_PULL,

    // Audit
    AUDIT_READ
}
