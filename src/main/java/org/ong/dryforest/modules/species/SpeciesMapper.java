package org.ong.dryforest.modules.species;

import org.ong.dryforest.modules.species.dto.SpeciesDTO;
import org.ong.dryforest.modules.species.dto.SpeciesTranslationDTO;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
public class SpeciesMapper {

    public SpeciesDTO toDTO(Species species) {
        var id = ServiceUtils.requireId(
                species.getId(),
                "Species ID must not be null"
        );

        var translations = species.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        var dto = new SpeciesDTO();
        dto.setId(id);
        dto.setScientificName(species.getScientificName());
        dto.setDensity(species.getDensity());

        if (species.getSpeciesType() != null) {
            dto.setSpeciesTypeId(
                    ServiceUtils.requireId(
                            species.getSpeciesType().getId(),
                            "SpeciesType ID must not be null"
                    )
            );
        }

        dto.setTranslations(translations);
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
        translation.setName(translationDTO.getName().trim());
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

        var dto = new SpeciesTranslationDTO();
        dto.setId(id);
        dto.setLocale(translation.getLocale());
        dto.setName(translation.getName());
        dto.setCreatedAt(translation.getCreatedAt());
        dto.setUpdatedAt(translation.getUpdatedAt());

        return dto;
    }
}
