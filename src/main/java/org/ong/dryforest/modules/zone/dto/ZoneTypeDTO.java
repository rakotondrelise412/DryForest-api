package org.ong.dryforest.modules.zone.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ong.dryforest.modules.zone.translations.ZoneTypeTranslationDTO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneTypeDTO {

    private Long id;

    private String name;

    private List<ZoneTypeTranslationDTO> translations;
}
