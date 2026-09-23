package org.ong.dryforest.modules.severity;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.severity.dto.SeverityDTO;
import org.ong.dryforest.modules.severity.dto.SeverityTranslationDTO;
import org.ong.dryforest.modules.severity.translations.SeverityTranslationRepository;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SeverityService {

    private final SeverityRepository severityRepository;
    private final SeverityTranslationRepository severityTranslationRepository;
    private final SeverityMapper severityMapper;


    @Transactional(readOnly = true)
    public Page<SeverityDTO> getAll(Pageable pageable) {

        return severityRepository.findAll(pageable)
                .map(severityMapper::toDTO);
    }


    @Transactional(readOnly = true)
    public SeverityDTO getById(Long id) {

        var severity = getSeverityOrThrow(id);

        return severityMapper.toDTO(severity);
    }


    public SeverityDTO create(SeverityDTO severityDTO) {

        var severity = new Severity();

        var savedSeverity = severityRepository.save(severity);

        saveTranslations(
                savedSeverity,
                severityDTO.getTranslations()
        );

        return severityMapper.toDTO(savedSeverity);
    }


    public SeverityDTO update(
            Long id,
            SeverityDTO severityDTO
    ) {

        var severity = getSeverityOrThrow(id);

        var oldTranslations =
                severityTranslationRepository
                        .findAllBySeverity_Id(id);

        severityTranslationRepository.deleteAll(oldTranslations);

        saveTranslations(
                severity,
                severityDTO.getTranslations()
        );

        return severityMapper.toDTO(severity);
    }


    public void delete(Long id) {

        var severity = getSeverityOrThrow(id);

        severityRepository.delete(severity);
    }


    private Severity getSeverityOrThrow(Long id) {

        return ServiceUtils.getOrThrow(
                () -> severityRepository.findById(id),
                ErrorCode.SEVERITY_NOT_FOUND,
                "Severity not found with id: " + id
        );
    }


    private void saveTranslations(
            Severity severity,
            List<SeverityTranslationDTO> translationDTOs
    ) {

        if (translationDTOs.isEmpty()) {
            return;
        }

        for (var translationDTO : translationDTOs) {

            var translation = severityMapper.toTranslation(
                    severity,
                    translationDTO
            );

            severityTranslationRepository.save(translation);

            severity.getTranslations().add(translation);
        }
    }
}