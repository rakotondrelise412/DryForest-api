package org.ong.dryforest.modules.species.speciesType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.species.speciesType.dto.SpeciesTypeDTO;
import org.ong.dryforest.modules.species.speciesType.dto.SpeciesTypeTranslationDTO;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpeciesTypeMapper {

    public SpeciesTypeDTO toDTO(SpeciesType speciesType) {

        var id = ServiceUtils.requireId(
                speciesType.getId(),
                "SpeciesType ID must not be null"
        );

        var translations = speciesType.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        var dto = new SpeciesTypeDTO(id, translations);

        dto.setCreatedAt(speciesType.getCreatedAt());
        dto.setUpdatedAt(speciesType.getUpdatedAt());

        return dto;
    }

    public SpeciesTypeTranslation toTranslation(
            SpeciesType speciesType,
            SpeciesTypeTranslationDTO translationDTO
    ) {
        var translation = new SpeciesTypeTranslation();

        translation.setLocale(translationDTO.getLocale());
        translation.setName(translationDTO.getName());
        translation.setSpeciesType(speciesType);

        return translation;
    }

    private SpeciesTypeTranslationDTO translationToDTO(
            SpeciesTypeTranslation translation
    ) {
        var id = ServiceUtils.requireId(
                translation.getId(),
                "SpeciesTypeTranslation ID must not be null"
        );

        var dto = new SpeciesTypeTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );

        dto.setCreatedAt(translation.getCreatedAt());
        dto.setUpdatedAt(translation.getUpdatedAt());

        return dto;
    }
}
