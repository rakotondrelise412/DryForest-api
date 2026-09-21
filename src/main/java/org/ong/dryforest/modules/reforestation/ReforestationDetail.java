package org.ong.dryforest.modules.reforestation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.species.Species;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.UUID;

@Entity
@Table(name = "reforestation_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReforestationDetail extends AbstractEntity {
    private Double quantity = 0.0d;
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @ManyToOne
    private @Nullable Reforestation reforestation;

    @ManyToOne
    private @Nullable  Species species;
}
