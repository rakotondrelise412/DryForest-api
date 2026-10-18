package org.ong.dryforest.modules.zone.zoneNeed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
