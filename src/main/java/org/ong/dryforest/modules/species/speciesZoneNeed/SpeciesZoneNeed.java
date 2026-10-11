package org.ong.dryforest.modules.species.speciesZoneNeed;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.ong.dryforest.modules.species.Species;
import org.ong.dryforest.modules.zone.zoneNeed.ZoneNeed;
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
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_species", nullable = false)
    private Species species;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_zone_need", nullable = false)
    private ZoneNeed zoneNeed;
}