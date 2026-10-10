package org.ong.dryforest.modules.species;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.species.dto.SpeciesDTO;
import org.ong.dryforest.modules.species.dto.SpeciesTranslationDTO;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpeciesMapper {

    public SpeciesDTO toDTO(Species species) {

        var id = ServiceUtils.requireId(
                species.getId(),
                "Species ID must not be null"
        );

        Long speciesTypeId = species.getSpeciesType() != null
                ? species.getSpeciesType().getId()
                : null;

        var translations = species.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        assert speciesTypeId != null;
        var dto = new SpeciesDTO(
                id,
                species.getScientificName(),
                species.getDensity(),
                speciesTypeId,
                translations
        );

        dto.setCreatedAt(species.getCreatedAt());
        dto.setUpdatedAt(species.getUpdatedAt());

        return dto;
    }

    public SpeciesTranslation toTranslation(
            Species species,
            SpeciesTranslationDTO translationDTO
    ) {
        var translation = new SpeciesTranslation();

        translation.setLocale(translationDTO.getLocale());
        translation.setName(translationDTO.getName());
        translation.setSpecies(species);

        return translation;
    }

    private SpeciesTranslationDTO translationToDTO(
            SpeciesTranslation translation
    ) {
        var id = ServiceUtils.requireId(
                translation.getId(),
                "SpeciesTranslation ID must not be null"
        );

        var dto = new SpeciesTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );

        dto.setCreatedAt(translation.getCreatedAt());
        dto.setUpdatedAt(translation.getUpdatedAt());

        return dto;
    }
}
