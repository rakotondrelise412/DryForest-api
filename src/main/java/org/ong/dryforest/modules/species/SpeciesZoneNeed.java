package org.ong.dryforest.modules.species;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.zone.ZoneNeed;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "species_zone_need")
public class SpeciesZoneNeed extends AbstractEntity {
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @ManyToOne
    private @Nullable  Species species;

    @ManyToOne
    private @Nullable ZoneNeed zoneNeed;

}
