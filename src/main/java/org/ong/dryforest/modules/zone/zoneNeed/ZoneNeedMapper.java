
package org.ong.dryforest.modules.zone.zoneNeed;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ZoneNeedMapper {

    public ZoneNeedDTO toDTO(ZoneNeed entity) {
        ZoneNeedDTO dto = new ZoneNeedDTO();

        dto.setId(Objects.requireNonNull(entity.getId()));
        dto.setUuid(entity.getUuid());
        dto.setIsSynced(entity.getIsSynced());

        dto.setZoneId(
                Objects.requireNonNull(entity.getZone().getId())
        );

        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public ZoneNeed toEntity(ZoneNeedDTO dto) {
        ZoneNeed entity = new ZoneNeed();

        entity.setUuid(dto.getUuid());
        entity.setIsSynced(dto.getIsSynced());

        return entity;
    }
}
