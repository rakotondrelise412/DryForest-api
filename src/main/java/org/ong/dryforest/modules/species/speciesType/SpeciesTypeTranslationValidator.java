
package org.ong.dryforest.modules.species.speciesType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.modules.species.speciesType.dto.SpeciesTypeTranslationDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SpeciesTypeTranslationValidator {

    private final SpeciesTypeRepository speciesTypeRepository;

    public void validateUniqueTranslationsForCreate(
            List<SpeciesTypeTranslationDTO> translationDTOs
    ) {
        validateTranslationList(translationDTOs);

        for (SpeciesTypeTranslationDTO dto : translationDTOs) {
            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            if (speciesTypeRepository.existsDuplicateTranslation(name)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans un type d'espèce."
                );
            }
        }
    }

    public void validateUniqueTranslationsForUpdate(
            SpeciesType speciesType,
            List<SpeciesTypeTranslationDTO> translationDTOs,
            Long speciesTypeId
    ) {
        validateTranslationList(translationDTOs);

        for (SpeciesTypeTranslationDTO dto : translationDTOs) {
            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            SpeciesTypeTranslation existingTranslation =
                    speciesType.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (existingTranslation != null
                    && existingTranslation.getName().trim()
                    .equalsIgnoreCase(name)) {
                continue;
            }

            if (speciesTypeRepository
                    .existsDuplicateTranslationForUpdate(
                            name, speciesTypeId)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans un autre type d'espèce."
                );
            }
        }
    }

    public void ensureFrenchTranslation(SpeciesType speciesType) {
        boolean frenchExists = speciesType.getTranslations().stream()
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
            List<SpeciesTypeTranslationDTO> translationDTOs
    ) {
        Set<Locale> submittedLocales = new HashSet<>();

        for (SpeciesTypeTranslationDTO dto : translationDTOs) {
            if (!submittedLocales.add(dto.getLocale())) {
                throw new IllegalArgumentException(
                        "Une seule traduction par langue est autorisée."
                );
            }
        }
    }
}
