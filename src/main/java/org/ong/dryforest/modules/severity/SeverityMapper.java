package org.ong.dryforest.modules.severity;

import org.ong.dryforest.modules.severity.dto.SeverityDTO;
import org.ong.dryforest.modules.severity.dto.SeverityTranslationDTO;
import org.ong.dryforest.modules.severity.translations.SeverityTranslation;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
public class SeverityMapper {

    public SeverityDTO toDTO(Severity severity) {

        var id = ServiceUtils.requireId(
                severity.getId(),
                "Severity ID must not be null"
        );

        var translations = severity.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        return new SeverityDTO(
                id,
                translations
        );
    }


    public SeverityTranslation toTranslation(
            Severity severity,
            SeverityTranslationDTO translationDTO
    ) {

        var translation = new SeverityTranslation();

        translation.setLocale(
                translationDTO.getLocale()
        );

        translation.setName(
                translationDTO.getName()
        );

        translation.setSeverity(severity);

        return translation;
    }


    private SeverityTranslationDTO translationToDTO(
            SeverityTranslation translation
    ) {

        var id = ServiceUtils.requireId(
                translation.getId(),
                "SeverityTranslation ID must not be null"
        );

        return new SeverityTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );
    }
}