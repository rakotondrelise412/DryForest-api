package org.ong.dryforest.modules.patrol.incidentType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.modules.patrol.incidentType.dto.IncidentTypeTranslationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IncidentTypeTranslationValidator {

    private final IncidentTypeRepository incidentTypeRepository;

    public void validateUniqueTranslationsForCreate(
            List<IncidentTypeTranslationDTO> translationDTOs
    ) {

        for (IncidentTypeTranslationDTO dto : translationDTOs) {

            if (dto.getName() == null || dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            if (incidentTypeRepository.existsDuplicateTranslation(name)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans un type d'incident."
                );
            }
        }
    }

    public void validateUniqueTranslationsForUpdate(
            IncidentType incidentType,
            List<IncidentTypeTranslationDTO> translationDTOs,
            Long incidentTypeId
    ) {

        for (IncidentTypeTranslationDTO dto : translationDTOs) {

            if (dto.getName() == null || dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            IncidentTypeTranslation existingTranslation =
                    incidentType.getTranslations()
                            .stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (existingTranslation != null
                    && existingTranslation.getName().trim()
                    .equalsIgnoreCase(name)) {
                continue;
            }

            if (incidentTypeRepository.existsDuplicateTranslationForUpdate(
                    name,
                    incidentTypeId
            )) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans un autre type d'incident."
                );
            }
        }
    }

    public void ensureFrenchTranslation(IncidentType incidentType) {

        boolean frenchExists = incidentType.getTranslations()
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
}
