package org.ong.dryforest.modules.zone.dto;

import lombok.*;
import org.ong.dryforest.shared.base.AbstractTimestamps;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDTO extends AbstractTimestamps {

    private Long id;

    private UUID uuid;

    private Double area;

    private Boolean isSynced;

    private Long typeZoneId;

    private Long siteId;

    private PolygonDTO geom;

    private List<ZoneTranslationDTO> translations;
}
