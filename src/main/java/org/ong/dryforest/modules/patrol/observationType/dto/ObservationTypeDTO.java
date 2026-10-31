package org.ong.dryforest.modules.patrol.observationType.dto;

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
public class ObservationTypeDTO extends AbstractTimestamps {

    private Long id;

    private List<ObservationTypeTranslationDTO> translations;
}