package org.ong.dryforest.modules.zone.dto;

import lombok.*;
import org.ong.dryforest.shared.base.AbstractTimestamps;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneNeedDTO extends AbstractTimestamps {

    private Long id;

    private UUID uuid;

    private Boolean isSynced;

    private Long zoneId;
}
