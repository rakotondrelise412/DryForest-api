package org.ong.dryforest.modules.zone;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.locationtech.jts.geom.Polygon;
import org.ong.dryforest.modules.plantation.PlantationBlock;
import org.ong.dryforest.modules.zone.translations.ZoneTranslation;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "zones")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Zone extends AbstractEntity {
    private Double area = 0.0;
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @Column(columnDefinition = "GEOMETRY(polygon, 4326)")
    private @Nullable Polygon geom;

    @ManyToOne
    private @Nullable ZoneType typeZone;

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlantationBlock> plantationBlocks = new ArrayList<>();

    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ZoneTranslation> translations = new ArrayList<>();
}
