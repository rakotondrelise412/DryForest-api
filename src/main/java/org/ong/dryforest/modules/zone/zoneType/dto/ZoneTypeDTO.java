package org.ong.dryforest.modules.zone.zoneType.dto;


import lombok.*;
import org.ong.dryforest.shared.base.AbstractTimestamps;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneTypeDTO extends AbstractTimestamps {

    private Long id;

    private String name;

    private List<ZoneTypeTranslationDTO> translations;
}
