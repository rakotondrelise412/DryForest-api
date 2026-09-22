package org.ong.dryforest.modules.severity.service;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.severity.Severity;
import org.ong.dryforest.modules.severity.SeverityRepository;
import org.ong.dryforest.modules.severity.dto.SeverityDTO;
import org.ong.dryforest.modules.severity.dto.SeverityTranslationDTO;
import org.ong.dryforest.modules.severity.translations.SeverityTranslation;
import org.ong.dryforest.modules.severity.translations.SeverityTranslationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeverityServiceImpl implements SeverityService {

    private final SeverityRepository severityRepository;

    private final SeverityTranslationRepository severityTranslationRepository;


    @Override
    @Transactional(readOnly = true)
    public List<SeverityDTO> getAll() {

        return severityRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public SeverityDTO getById(Long id) {

        Severity severity = severityRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Severity not found with id: " + id
                        )
                );

        return toDTO(severity);
    }


    @Override
    public SeverityDTO create(SeverityDTO severityDTO) {

        Severity severity = new Severity();
        Severity savedSeverity =
                severityRepository.save(severity);
        saveTranslations(
                savedSeverity,
                severityDTO.getTranslations()
        );
        return toDTO(savedSeverity);
    }


    @Override
    public SeverityDTO update(
            Long id,
            SeverityDTO severityDTO
    ) {

        Severity severity = severityRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Severity not found with id: " + id
                        )
                );

        List<SeverityTranslation> oldTranslations =
                severityTranslationRepository
                        .findAllBySeverity_Id(id);

        severityTranslationRepository.deleteAll(
                oldTranslations
        );

        saveTranslations(
                severity,
                severityDTO.getTranslations()
        );

        return toDTO(severity);
    }


    @Override
    public void delete(Long id) {

        Severity severity = severityRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Severity not found with id: " + id
                        )
                );

        severityRepository.delete(severity);
    }


    private void saveTranslations(
            Severity severity,
            List<SeverityTranslationDTO> translationDTOs
    ) {

        if (translationDTOs == null ||
                translationDTOs.isEmpty()) {

            return;
        }

        for (SeverityTranslationDTO translationDTO :
                translationDTOs) {

            SeverityTranslation translation =
                    new SeverityTranslation();

            translation.setLocale(
                    translationDTO.getLocale()
            );

            translation.setName(
                    translationDTO.getName()
            );

            translation.setSeverity(severity);

            severityTranslationRepository.save(
                    translation
            );
        }
    }

    private SeverityDTO toDTO(
            Severity severity
    ) {

        List<SeverityTranslationDTO> translations =
                severityTranslationRepository
                        .findAllBySeverity_Id(
                                severity.getId()
                        )
                        .stream()
                        .map(this::translationToDTO)
                        .toList();

        return new SeverityDTO(
                severity.getId(),
                translations
        );
    }



    private SeverityTranslationDTO translationToDTO(
            SeverityTranslation translation
    ) {

        return new SeverityTranslationDTO(
                translation.getId(),
                translation.getLocale(),
                translation.getName()
        );
    }
}