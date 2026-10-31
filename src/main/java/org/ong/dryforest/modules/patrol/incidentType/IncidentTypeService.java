package org.ong.dryforest.modules.patrol.incidentType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.patrol.incidentType.dto.IncidentTypeDTO;
import org.ong.dryforest.modules.patrol.incidentType.dto.IncidentTypeTranslationDTO;
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
public class IncidentTypeService {

    private final IncidentTypeRepository incidentTypeRepository;
    private final IncidentTypeMapper incidentTypeMapper;
    private final IncidentTypeTranslationValidator translationValidator;

    @Transactional(readOnly = true)
    public Page<IncidentTypeDTO> getAll(Pageable pageable) {

        return incidentTypeRepository.findAll(pageable)
                .map(incidentTypeMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public IncidentTypeDTO getById(Long id) {

        return incidentTypeMapper.toDTO(getIncidentTypeOrThrow(id));
    }

    public IncidentTypeDTO create(IncidentTypeDTO incidentTypeDTO) {

        translationValidator.validateUniqueTranslationsForCreate(
                incidentTypeDTO.getTranslations()
        );

        IncidentType incidentType = new IncidentType();

        saveTranslations(
                incidentType,
                incidentTypeDTO.getTranslations()
        );

        translationValidator.ensureFrenchTranslation(incidentType);

        IncidentType savedIncidentType =
                incidentTypeRepository.save(incidentType);

        return incidentTypeMapper.toDTO(savedIncidentType);
    }

    public IncidentTypeDTO update(
            Long id,
            IncidentTypeDTO incidentTypeDTO
    ) {

        IncidentType incidentType = getIncidentTypeOrThrow(id);

        translationValidator.validateUniqueTranslationsForUpdate(
                incidentType,
                incidentTypeDTO.getTranslations(),
                id
        );

        saveTranslations(
                incidentType,
                incidentTypeDTO.getTranslations()
        );

        translationValidator.ensureFrenchTranslation(incidentType);

        incidentTypeRepository.flush();

        return incidentTypeMapper.toDTO(incidentType);
    }

    public void delete(Long id) {

        IncidentType incidentType = getIncidentTypeOrThrow(id);

        incidentTypeRepository.delete(incidentType);
    }

    @Transactional(readOnly = true)
    public SearchResult<IncidentTypeDTO> search(
            IncidentTypeSearchQuery request
    ) {

        return SearchSpecification
                .filterBy(incidentTypeRepository, request)
                .map(incidentTypeMapper::toDTO);
    }

    private IncidentType getIncidentTypeOrThrow(Long id) {

        return ServiceUtils.getOrThrow(
                () -> incidentTypeRepository.findById(id),
                ErrorCode.INCIDENT_TYPE_NOT_FOUND,
                "IncidentType not found with id: " + id
        );
    }

    private void saveTranslations(
            IncidentType incidentType,
            List<IncidentTypeTranslationDTO> translationDTOs
    ) {

        for (IncidentTypeTranslationDTO dto : translationDTOs) {

            if (dto.getName() == null || dto.getName().isBlank()) {
                continue;
            }

            IncidentTypeTranslation translation =
                    incidentType.getTranslations()
                            .stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (translation == null) {

                translation = incidentTypeMapper.toTranslation(
                        incidentType,
                        dto
                );

                incidentType.getTranslations().add(translation);

            } else {

                translation.setName(dto.getName().trim());
            }
        }
    }
}
