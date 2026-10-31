package org.ong.dryforest.modules.patrol.dto;

import lombok.*;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.shared.base.AbstractTimestamps;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatrolGroupTranslationDTO extends AbstractTimestamps {

    private Long id;
    private Locale locale;
    private String name;
}