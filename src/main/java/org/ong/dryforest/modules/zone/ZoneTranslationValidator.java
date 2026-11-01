package org.ong.dryforest.modules.zone;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.modules.zone.dto.ZoneTranslationDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ZoneTranslationValidator {

    private final ZoneRepository zoneRepository;

    public void validateUniqueTranslationsForCreate(
            List<ZoneTranslationDTO> translationDTOs
    ) {

        validateTranslations(translationDTOs);

        for (ZoneTranslationDTO dto : translationDTOs) {

            String name = dto.getName().trim();

            if (zoneRepository.existsDuplicateTranslation(
                    name,
                    dto.getLocale()
            )) {

                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà pour une zone "
                                + "dans la langue " + dto.getLocale() + "."
                );
            }
        }
    }


    public void validateUniqueTranslationsForUpdate(
            Zone zone,
            List<ZoneTranslationDTO> translationDTOs,
            Long zoneId
    ) {

        validateTranslations(translationDTOs);

        for (ZoneTranslationDTO dto : translationDTOs) {

            String name = dto.getName().trim();

            ZoneTranslation existingTranslation =
                    zone.getTranslations()
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

            if (zoneRepository.existsDuplicateTranslationForUpdate(
                    name,
                    dto.getLocale(),
                    zoneId
            )) {

                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà pour une autre zone "
                                + "dans la langue " + dto.getLocale() + "."
                );
            }
        }
    }


    private void validateTranslations(
            List<ZoneTranslationDTO> translationDTOs
    ) {

        if (translationDTOs.isEmpty()) {

            throw new IllegalArgumentException(
                    "Au moins une traduction est obligatoire."
            );
        }

        Set<Locale> locales = new HashSet<>();

        for (ZoneTranslationDTO dto : translationDTOs) {

            if (!locales.add(dto.getLocale())) {

                throw new IllegalArgumentException(
                        "Une seule traduction par langue est autorisée : "
                                + dto.getLocale()
                );
            }
        }
    }
}
