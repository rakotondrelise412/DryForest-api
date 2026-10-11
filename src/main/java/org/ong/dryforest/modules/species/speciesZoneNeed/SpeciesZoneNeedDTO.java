package org.ong.dryforest.modules.species.speciesZoneNeed;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesZoneNeedDTO {

    private Long id;

    private UUID uuid;

    @JsonProperty("id_species")
    private Long idSpecies;

    @JsonProperty("id_zone_need")
    private Long idZoneNeed;

    @JsonProperty("is_synced")
    private Boolean isSynced;
}
