package org.ong.dryforest.modules.animal;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.modules.animal.translations.TrackingTranslation;
import org.ong.dryforest.modules.plantation.PlantationBlock;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "trackings")
public class Tracking extends AbstractEntity {
    private @Nullable LocalDateTime date;
    private Boolean haveSeen = false;
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @Column(columnDefinition = "GEOMETRY(Point, 4326)")
    private @Nullable Point location;

    @ManyToOne
    private @Nullable PlantationBlock plantationBlock;

    @OneToMany(mappedBy = "tracking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackingDetail> trackingDetails = new ArrayList<>();

    @OneToMany(mappedBy = "tracking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackingTranslation> translations = new ArrayList<>();
}
