package org.ong.dryforest.modules.severity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractTimestamps;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeverityDTO extends AbstractTimestamps {

    private Long id;

    private List<SeverityTranslationDTO> translations;
}