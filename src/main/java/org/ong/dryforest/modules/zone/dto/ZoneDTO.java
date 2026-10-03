package org.ong.dryforest.modules.zone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ong.dryforest.modules.zone.translations.ZoneTranslationDTO;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneDTO {

    private Long id;

    private UUID uuid;

    private Double area;

    private Boolean isSynced;

    private Long typeZoneId;

    private Long siteId;

    private PolygonDTO geom;

    private List<ZoneTranslationDTO> translations;
}
