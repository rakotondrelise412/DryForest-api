package org.ong.dryforest.modules.plantation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.plot.SubPlot;
import org.ong.dryforest.modules.reforestation.Reforestation;
import org.ong.dryforest.modules.species.Species;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "plantations",
        indexes = {
                @Index(columnList = "date")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Plantation extends AbstractEntity {
    private @Nullable String plantNumber;
    private @Nullable LocalDate date;
    private Double diameter = 0.0d;
    private Double height = 0.0d;
    private Double carbonSequestered = 0.0d;
    private @Nullable String image;
    private Boolean status = false;
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @ManyToOne
    private @Nullable Reforestation reforestation;

    @ManyToOne
    private @Nullable Species species;

    @ManyToOne
    private @Nullable SubPlot subPlot;
}
