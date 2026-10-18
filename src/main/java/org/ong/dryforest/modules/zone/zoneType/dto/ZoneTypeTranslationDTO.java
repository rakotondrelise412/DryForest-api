package org.ong.dryforest.modules.zone.zoneType.dto;

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
public class ZoneTypeTranslationDTO extends AbstractTimestamps {

    private Long id;

    private Locale locale;

    private String name;
}