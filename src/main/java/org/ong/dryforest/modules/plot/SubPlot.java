package org.ong.dryforest.modules.plot;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.modules.plantation.PlantationBlock;
import org.ong.dryforest.modules.plot.translations.SubPlotTranslation;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "sub_plots")
public class SubPlot extends AbstractEntity {
    private @Nullable Double width;
    private @Nullable Double length;
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @Column(columnDefinition = "GEOMETRY(Point, 4326)")
    private @Nullable Point location;

    @ManyToOne
    private @Nullable PlantationBlock plantationBlock;

    @OneToMany(mappedBy = "subPlot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubPlotTranslation> translations = new ArrayList<>();
}
