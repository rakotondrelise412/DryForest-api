package org.ong.dryforest.modules.patrol;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.modules.patrol.dto.PatrolGroupTranslationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PatrolGroupTranslationValidator {

    private final PatrolGroupRepository patrolGroupRepository;

    public void validateUniqueTranslationsForCreate(
            List<PatrolGroupTranslationDTO> translationDTOs
    ) {
        for (PatrolGroupTranslationDTO dto : translationDTOs) {

            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            if (patrolGroupRepository.existsDuplicateTranslation(name)) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans un groupe de patrouille."
                );
            }
        }
    }

    public void validateUniqueTranslationsForUpdate(
            PatrolGroup patrolGroup,
            List<PatrolGroupTranslationDTO> translationDTOs,
            Long patrolGroupId
    ) {
        for (PatrolGroupTranslationDTO dto : translationDTOs) {

            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            PatrolGroupTranslation existingTranslation =
                    patrolGroup.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (existingTranslation != null
                    && existingTranslation.getName().trim()
                    .equalsIgnoreCase(name)) {
                continue;
            }

            if (patrolGroupRepository.existsDuplicateTranslationForUpdate(
                    name, patrolGroupId
            )) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà dans un autre groupe de patrouille."
                );
            }
        }
    }

    public void ensureFrenchTranslation(PatrolGroup patrolGroup) {

        boolean frenchExists = patrolGroup.getTranslations().stream()
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