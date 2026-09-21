package org.ong.dryforest.modules.patrol;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.modules.identity.User;
import org.ong.dryforest.modules.patrol.translations.IncidentTranslation;
import org.ong.dryforest.modules.plantation.PlantationBlock;
import org.ong.dryforest.modules.severity.Severity;
import org.ong.dryforest.modules.zone.Zone;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "incidents")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Incident extends AbstractEntity {
    private @Nullable String image;
    private Boolean isSynced = false;
    private @Nullable LocalDateTime incidentAt;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @Column(columnDefinition = "GEOMETRY(Point,4326)")
    private @Nullable Point location;

    @ManyToOne(fetch = FetchType.LAZY)
    private @Nullable Severity severity;

    @ManyToOne
    private @Nullable IncidentType incidentType;

    @ManyToOne
    private @Nullable PatrolGroup patrolGroup;

    @ManyToOne
    private @Nullable User user;

    @ManyToOne
    private @Nullable Zone zone;

    @ManyToOne
    private @Nullable PlantationBlock plantationBlock;

    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncidentTranslation> translations = new ArrayList<>();
}
