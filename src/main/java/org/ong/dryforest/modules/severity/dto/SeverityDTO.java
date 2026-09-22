package org.ong.dryforest.modules.severity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeverityDTO {

    private Long id;

    private List<SeverityTranslationDTO> translations;
}