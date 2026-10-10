package org.ong.dryforest.modules.patrol.dto;

import lombok.*;
import org.ong.dryforest.shared.base.AbstractTimestamps;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ObservationTypeDTO extends AbstractTimestamps {

    private Long id;

    private List<ObservationTypeTranslationDTO> translations;
}
