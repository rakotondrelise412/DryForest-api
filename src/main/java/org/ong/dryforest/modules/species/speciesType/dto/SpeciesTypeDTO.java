package org.ong.dryforest.modules.species.speciesType.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractTimestamps;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesTypeDTO extends AbstractTimestamps {

    private Long id;
    private List<SpeciesTypeTranslationDTO> translations;
}

