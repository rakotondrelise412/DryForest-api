package org.ong.dryforest.modules.zone.translations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ong.dryforest.enums.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneTranslationDTO {

    private Long id;

    private Locale locale;

    private String name;
}
