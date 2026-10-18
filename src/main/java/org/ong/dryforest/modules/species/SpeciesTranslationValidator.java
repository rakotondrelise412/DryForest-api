
package org.ong.dryforest.modules.species;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.modules.species.dto.SpeciesTranslationDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SpeciesTranslationValidator {

    private final SpeciesRepository speciesRepository;

    public void validateUniqueTranslationsForCreate(
            List<SpeciesTranslationDTO> translationDTOs
    ) {
        validateTranslationList(translationDTOs);

        for (SpeciesTranslationDTO dto : translationDTOs) {
            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            if (speciesRepository.existsDuplicateTranslation(name)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans une autre espèce."
                );
            }
        }
    }

    public void validateUniqueTranslationsForUpdate(
            Species species,
            List<SpeciesTranslationDTO> translationDTOs,
            Long speciesId
    ) {
        validateTranslationList(translationDTOs);

        for (SpeciesTranslationDTO dto : translationDTOs) {
            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            var existingTranslation = species.getTranslations()
                    .stream()
                    .filter(t -> t.getLocale() == dto.getLocale())
                    .findFirst()
                    .orElse(null);

            if (existingTranslation != null
                    && existingTranslation.getName()
                    .trim()
                    .equalsIgnoreCase(name)) {
                continue;
            }

            if (speciesRepository.existsDuplicateTranslationForUpdate(
                    name, speciesId)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans une autre espèce."
                );
            }
        }
    }

    public void ensureFrenchTranslation(Species species) {
        boolean frenchExists = species.getTranslations()
                .stream()
                .anyMatch(t ->
                        t.getLocale() == Locale.FR
                                && !t.getName().isBlank()
                );

        if (!frenchExists) {
            throw new IllegalArgumentException(
                    "La traduction française (FR) est obligatoire."
            );
        }
    }

    private void validateTranslationList(
            List<SpeciesTranslationDTO> translationDTOs
    ) {
        Set<Locale> submittedLocales = new HashSet<>();

        for (SpeciesTranslationDTO dto : translationDTOs) {
            if (!submittedLocales.add(dto.getLocale())) {
                throw new IllegalArgumentException(
                        "Une seule traduction par langue est autorisée."
                );
            }
        }
    }
}
