package org.ong.dryforest.modules.site;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.site.dto.SiteTranslationDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SiteTranslationValidator {

    private final SiteRepository siteRepository;

    public void validateUniqueTranslationsForCreate(
            List<SiteTranslationDTO> translationDTOs
    ) {

        for (SiteTranslationDTO dto : translationDTOs) {

            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            if (siteRepository.existsDuplicateTranslation(
                    name,
                    dto.getLocale()
            )) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà pour un site "
                                + "dans la langue " + dto.getLocale() + "."
                );
            }
        }
    }

    public void validateUniqueTranslationsForUpdate(
            Site site,
            List<SiteTranslationDTO> translationDTOs,
            Long siteId
    ) {

        for (SiteTranslationDTO dto : translationDTOs) {

            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            SiteTranslation existingTranslation =
                    site.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (existingTranslation != null
                    && existingTranslation.getName()
                    .trim()
                    .equalsIgnoreCase(name)) {
                continue;
            }

            if (siteRepository.existsDuplicateTranslationForUpdate(
                    name,
                    dto.getLocale(),
                    siteId
            )) {
                throw new IllegalArgumentException(
                        "Le nom '" + name
                                + "' existe déjà pour un autre site "
                                + "dans la langue " + dto.getLocale() + "."
                );
            }
        }
    }
}
