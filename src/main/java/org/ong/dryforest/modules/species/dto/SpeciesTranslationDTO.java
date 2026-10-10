package org.ong.dryforest.modules.species.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.shared.base.AbstractTimestamps;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpeciesTranslationDTO extends AbstractTimestamps {

    private Long id;
    private Locale locale;
    private String name;
}