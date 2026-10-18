
package org.ong.dryforest.modules.species.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractTimestamps;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesDTO extends AbstractTimestamps {

    private Long id;

    private String scientificName;

    private Double density;

    private Long speciesTypeId;

    private List<SpeciesTranslationDTO> translations =
            new ArrayList<>();
}
