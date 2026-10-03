package org.ong.dryforest.modules.zone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZoneNeedDTO {

    private Long id;

    private UUID uuid;

    private Boolean isSynced;

    private Long zoneId;
}
