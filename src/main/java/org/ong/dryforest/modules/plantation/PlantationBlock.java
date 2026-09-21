package org.ong.dryforest.modules.plantation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.locationtech.jts.geom.Polygon;
import org.ong.dryforest.modules.plantation.translations.PlantationBlockTranslation;
import org.ong.dryforest.modules.plot.SubPlot;
import org.ong.dryforest.modules.zone.Zone;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "plantation_blocks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlantationBlock extends AbstractEntity {
    private @Nullable Double width;
    private @Nullable Double length;
    private Integer nbSubPlot = 0;
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @Column(name = "geom", columnDefinition = "GEOMETRY(polygon, 4326)")
    private @Nullable Polygon geom;

    @ManyToOne
    private @Nullable Zone zone;

    @OneToMany
    private List<SubPlot> subPlots = new ArrayList<>();

    @OneToMany(mappedBy = "plantationBlock", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlantationBlockTranslation> translations = new ArrayList<>();
}
