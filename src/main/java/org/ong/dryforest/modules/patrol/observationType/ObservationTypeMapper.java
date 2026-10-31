package org.ong.dryforest.modules.patrol.observationType;

import org.ong.dryforest.modules.patrol.observationType.dto.ObservationTypeDTO;
import org.ong.dryforest.modules.patrol.observationType.dto.ObservationTypeTranslationDTO;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
public class ObservationTypeMapper {

    public ObservationTypeDTO toDTO(ObservationType observationType) {

        var id = ServiceUtils.requireId(
                observationType.getId(),
                "ObservationType ID must not be null"
        );

        var translations = observationType.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        var dto = new ObservationTypeDTO(id, translations);

        dto.setCreatedAt(observationType.getCreatedAt());
        dto.setUpdatedAt(observationType.getUpdatedAt());

        return dto;
    }

    public ObservationTypeTranslation toTranslation(
            ObservationType observationType,
            ObservationTypeTranslationDTO translationDTO
    ) {
        var translation = new ObservationTypeTranslation();

        translation.setLocale(translationDTO.getLocale());
        translation.setName(translationDTO.getName().trim());
        translation.setObservationType(observationType);

        return translation;
    }

    private ObservationTypeTranslationDTO translationToDTO(
            ObservationTypeTranslation translation
    ) {
        var id = ServiceUtils.requireId(
                translation.getId(),
                "ObservationTypeTranslation ID must not be null"
        );

        var dto = new ObservationTypeTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );

        dto.setCreatedAt(translation.getCreatedAt());
        dto.setUpdatedAt(translation.getUpdatedAt());

        return dto;
    }
}
