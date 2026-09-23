package org.ong.dryforest.modules.patrol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObservationTypeTranslationDTO {

    private Long id;

    private String locale;

    private String name;
}