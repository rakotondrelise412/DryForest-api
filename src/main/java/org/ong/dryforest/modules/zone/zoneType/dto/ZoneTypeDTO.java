package org.ong.dryforest.modules.zone.zoneType.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractTimestamps;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneTypeDTO extends AbstractTimestamps {

    private Long id;

    private String name;

    private List<ZoneTypeTranslationDTO> translations = new ArrayList<>();
}