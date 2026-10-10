package org.ong.dryforest.modules.site.dto;

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
public class SiteDTO extends AbstractTimestamps {

    private Long id;

    private List<SiteTranslationDTO> translations;

    private PointDTO location;
}