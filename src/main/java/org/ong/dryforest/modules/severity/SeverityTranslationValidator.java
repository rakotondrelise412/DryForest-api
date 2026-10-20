package org.ong.dryforest.modules.severity;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.modules.severity.dto.SeverityTranslationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SeverityTranslationValidator {

    private final SeverityRepository severityRepository;

    public void validateUniqueTranslationsForCreate(
            List<SeverityTranslationDTO> translationDTOs) {

        for (SeverityTranslationDTO dto : translationDTOs) {
            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            if (severityRepository.existsDuplicateTranslation(name)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans un niveau de sévérité."
                );
            }
        }
    }

    public void validateUniqueTranslationsForUpdate(
            Severity severity,
            List<SeverityTranslationDTO> translationDTOs,
            Long severityId) {

        for (SeverityTranslationDTO dto : translationDTOs) {
            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            SeverityTranslation existingTranslation =
                    severity.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (existingTranslation != null
                    && existingTranslation.getName().trim()
                    .equalsIgnoreCase(name)) {
                continue;
            }

            if (severityRepository.existsDuplicateTranslationForUpdate(
                    name, severityId)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans un autre niveau de sévérité."
                );
            }
        }
    }

    public void ensureFrenchTranslation(Severity severity) {
        boolean frenchExists = severity.getTranslations().stream()
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
