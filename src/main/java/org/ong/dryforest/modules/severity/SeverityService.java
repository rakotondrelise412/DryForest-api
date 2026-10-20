
package org.ong.dryforest.modules.severity;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.severity.dto.SeverityDTO;
import org.ong.dryforest.modules.severity.dto.SeverityTranslationDTO;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.search.SearchResult;
import org.ong.dryforest.shared.search.SearchSpecification;
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
    private final SeverityMapper severityMapper;
    private final SeverityTranslationValidator translationValidator;

    @Transactional(readOnly = true)
    public Page<SeverityDTO> getAll(Pageable pageable) {
        return severityRepository.findAll(pageable)
                .map(severityMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public SeverityDTO getById(Long id) {
        return severityMapper.toDTO(getSeverityOrThrow(id));
    }

    public SeverityDTO create(SeverityDTO severityDTO) {
        translationValidator.validateUniqueTranslationsForCreate(
                severityDTO.getTranslations()
        );

        Severity severity = new Severity();

        saveTranslations(severity, severityDTO.getTranslations());

        translationValidator.ensureFrenchTranslation(severity);

        Severity savedSeverity = severityRepository.save(severity);

        return severityMapper.toDTO(savedSeverity);
    }

    public SeverityDTO update(Long id, SeverityDTO severityDTO) {
        Severity severity = getSeverityOrThrow(id);

        translationValidator.validateUniqueTranslationsForUpdate(
                severity,
                severityDTO.getTranslations(),
                id
        );

        saveTranslations(severity, severityDTO.getTranslations());

        translationValidator.ensureFrenchTranslation(severity);

        severityRepository.flush();

        return severityMapper.toDTO(severity);
    }

    public void delete(Long id) {
        Severity severity = getSeverityOrThrow(id);
        severityRepository.delete(severity);
    }

    @Transactional(readOnly = true)
    public SearchResult<SeverityDTO> search(
            SeveritySearchQuery request) {

        return SearchSpecification
                .filterBy(severityRepository, request)
                .map(severityMapper::toDTO);
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
            List<SeverityTranslationDTO> translationDTOs) {

        for (SeverityTranslationDTO dto : translationDTOs) {
            if (dto.getName().isBlank()) {
                continue;
            }

            SeverityTranslation translation =
                    severity.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (translation == null) {
                translation = severityMapper.toTranslation(severity, dto);
                severity.getTranslations().add(translation);
            } else {
                translation.setName(dto.getName().trim());
            }
        }
    }
}
