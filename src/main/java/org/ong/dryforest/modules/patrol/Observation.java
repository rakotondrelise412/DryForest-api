package org.ong.dryforest.modules.patrol;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.modules.identity.User;
import org.ong.dryforest.modules.patrol.translations.ObservationTranslation;
import org.ong.dryforest.modules.zone.Zone;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "observations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Observation extends AbstractEntity {
    private @Nullable LocalDateTime date;
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @Column(name = "location", columnDefinition = "GEOMETRY(Point, 4326)")
    private @Nullable Point location;

    @ManyToOne
    private @Nullable ObservationType observationType;

    @ManyToOne
    private @Nullable PatrolGroup patrolGroup;

    @ManyToOne
    private @Nullable User user;

    @ManyToOne
    private @Nullable Zone zone;

    @OneToMany(mappedBy = "observation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ObservationTranslation> translations = new ArrayList<>();
}
