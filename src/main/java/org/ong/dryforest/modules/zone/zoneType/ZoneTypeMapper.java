package org.ong.dryforest.modules.zone.zoneType;

import org.ong.dryforest.modules.zone.zoneType.dto.ZoneTypeDTO;
import org.ong.dryforest.modules.zone.zoneType.dto.ZoneTypeTranslationDTO;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
public class ZoneTypeMapper {

    public ZoneTypeDTO toDTO(ZoneType zoneType) {
        var id = ServiceUtils.requireId(
                zoneType.getId(),
                "ZoneType ID must not be null"
        );

        var translations = zoneType.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        var dto = new ZoneTypeDTO(
                id,
                zoneType.getName(),
                translations
        );

        dto.setCreatedAt(zoneType.getCreatedAt());
        dto.setUpdatedAt(zoneType.getUpdatedAt());

        return dto;
    }

    public ZoneType toEntity(ZoneTypeDTO dto) {
        var zoneType = new ZoneType();
        zoneType.setName(dto.getName());
        return zoneType;
    }

    public ZoneTypeTranslation toTranslation(
            ZoneType zoneType,
            ZoneTypeTranslationDTO translationDTO
    ) {
        var translation = new ZoneTypeTranslation();

        translation.setLocale(translationDTO.getLocale());
        translation.setName(translationDTO.getName().trim());
        translation.setZoneType(zoneType);

        return translation;
    }

    private ZoneTypeTranslationDTO translationToDTO(
            ZoneTypeTranslation translation
    ) {
        var id = ServiceUtils.requireId(
                translation.getId(),
                "ZoneTypeTranslation ID must not be null"
        );

        var dto = new ZoneTypeTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );

        dto.setCreatedAt(translation.getCreatedAt());
        dto.setUpdatedAt(translation.getUpdatedAt());

        return dto;
    }
}