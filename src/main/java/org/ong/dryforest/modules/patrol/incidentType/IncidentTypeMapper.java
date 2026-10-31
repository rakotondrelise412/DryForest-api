package org.ong.dryforest.modules.patrol.incidentType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.patrol.incidentType.dto.IncidentTypeDTO;
import org.ong.dryforest.modules.patrol.incidentType.dto.IncidentTypeTranslationDTO;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IncidentTypeMapper {

    public IncidentTypeDTO toDTO(IncidentType incidentType) {

        var id = ServiceUtils.requireId(
                incidentType.getId(),
                "IncidentType ID must not be null"
        );

        var translations = incidentType.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        var dto = new IncidentTypeDTO(id, translations);

        dto.setCreatedAt(incidentType.getCreatedAt());
        dto.setUpdatedAt(incidentType.getUpdatedAt());

        return dto;
    }

    public IncidentTypeTranslation toTranslation(
            IncidentType incidentType,
            IncidentTypeTranslationDTO translationDTO
    ) {

        var translation = new IncidentTypeTranslation();

        translation.setLocale(translationDTO.getLocale());
        translation.setName(translationDTO.getName().trim());
        translation.setIncidentType(incidentType);

        return translation;
    }

    private IncidentTypeTranslationDTO translationToDTO(
            IncidentTypeTranslation translation
    ) {

        var id = ServiceUtils.requireId(
                translation.getId(),
                "IncidentTypeTranslation ID must not be null"
        );

        var dto = new IncidentTypeTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );

        dto.setCreatedAt(translation.getCreatedAt());
        dto.setUpdatedAt(translation.getUpdatedAt());

        return dto;
    }
}