package org.ong.dryforest.modules.zone.dto;

import lombok.*;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.shared.base.AbstractTimestamps;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneTranslationDTO extends AbstractTimestamps {

    private Long id;

    @NotNull(message = "La langue est obligatoire.")
    private Locale locale;

    @NotBlank(message = "Le nom est obligatoire.")
    private String name;
}
