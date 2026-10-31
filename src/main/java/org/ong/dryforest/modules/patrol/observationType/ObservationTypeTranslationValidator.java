package org.ong.dryforest.modules.patrol.observationType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.modules.patrol.observationType.dto.ObservationTypeTranslationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ObservationTypeTranslationValidator {

    private final ObservationTypeRepository observationTypeRepository;

    public void validateUniqueTranslationsForCreate(
            List<ObservationTypeTranslationDTO> translationDTOs
    ) {
        for (ObservationTypeTranslationDTO dto : translationDTOs) {

            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            if (observationTypeRepository.existsDuplicateTranslation(name)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans un type d'observation."
                );
            }
        }
    }

    public void validateUniqueTranslationsForUpdate(
            ObservationType observationType,
            List<ObservationTypeTranslationDTO> translationDTOs,
            Long observationTypeId
    ) {
        for (ObservationTypeTranslationDTO dto : translationDTOs) {

            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            ObservationTypeTranslation existingTranslation =
                    observationType.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (existingTranslation != null
                    && existingTranslation.getName().trim()
                    .equalsIgnoreCase(name)) {
                continue;
            }

            if (observationTypeRepository.existsDuplicateTranslationForUpdate(
                    name,
                    observationTypeId
            )) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans un autre type d'observation."
                );
            }
        }
    }

    public void ensureFrenchTranslation(ObservationType observationType) {

        boolean frenchExists = observationType.getTranslations().stream()
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
}