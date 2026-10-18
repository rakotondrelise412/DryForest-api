
package org.ong.dryforest.modules.zone.zoneType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.enums.Locale;
import org.ong.dryforest.modules.zone.zoneType.dto.ZoneTypeTranslationDTO;
import org.ong.dryforest.shared.exceptions.ApiException;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ZoneTypeTranslationValidator {

    private final ZoneTypeRepository zoneTypeRepository;

    public void validateForCreate(
            List<ZoneTypeTranslationDTO> translations
    ) {
        validateFrenchTranslation(translations);

        Set<String> names = new HashSet<>();

        for (ZoneTypeTranslationDTO dto : translations) {
            if (!StringUtils.hasText(dto.getName())) {
                continue;
            }

            String name = normalize(dto.getName(), dto.getLocale());

            if (!names.add(name)) {
                throw new ApiException(
                        HttpStatus.CONFLICT,
                        ErrorCode.ZONE_TYPE_TRANSLATION_ALREADY_EXISTS,
                        "Duplicate translation name: " + dto.getName()
                );
            }

            if (zoneTypeRepository.existsTranslationName(
                    dto.getLocale(),
                    dto.getName().trim()
            )) {
                throw new ApiException(
                        HttpStatus.CONFLICT,
                        ErrorCode.ZONE_TYPE_TRANSLATION_ALREADY_EXISTS,
                        "Translation name already exists: " + dto.getName()
                );
            }
        }
    }

    public void validateForUpdate(
            Long zoneTypeId,
            List<ZoneTypeTranslationDTO> translations
    ) {
        validateFrenchTranslation(translations);

        Set<String> names = new HashSet<>();

        for (ZoneTypeTranslationDTO dto : translations) {
            if (!StringUtils.hasText(dto.getName())) {
                continue;
            }

            String name = normalize(dto.getName(), dto.getLocale());

            if (!names.add(name)) {
                throw new ApiException(
                        HttpStatus.CONFLICT,
                        ErrorCode.ZONE_TYPE_TRANSLATION_ALREADY_EXISTS,
                        "Duplicate translation name: " + dto.getName()
                );
            }

            if (zoneTypeRepository.existsTranslationNameForOtherZoneType(
                    dto.getLocale(),
                    dto.getName().trim(),
                    zoneTypeId
            )) {
                throw new ApiException(
                        HttpStatus.CONFLICT,
                        ErrorCode.ZONE_TYPE_TRANSLATION_ALREADY_EXISTS,
                        "Translation name already exists: " + dto.getName()
                );
            }
        }
    }

    private void validateFrenchTranslation(
            List<ZoneTypeTranslationDTO> translations
    ) {
        boolean hasFrench = translations.stream()
                .anyMatch(dto ->
                        dto.getLocale() == Locale.FR
                                && StringUtils.hasText(dto.getName())
                );

        if (!hasFrench) {
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    ErrorCode.ZONE_TYPE_FRENCH_TRANSLATION_REQUIRED,
                    "A French translation is required"
            );
        }
    }

    private String normalize(String name, Locale locale) {
        return locale.name() + ":"
                + name.trim().toLowerCase(java.util.Locale.ROOT);
    }
}
