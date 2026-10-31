package org.ong.dryforest.modules.patrol.incident;


import lombok.*;
import org.ong.dryforest.shared.base.AbstractTimestamps;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncidentTranslationDTO extends AbstractTimestamps {

    private Long id;

    private String locale;

    private String description;
}
