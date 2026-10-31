package org.ong.dryforest.modules.patrol.observationType;


import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.patrol.observationType.dto.ObservationTypeDTO;
import org.ong.dryforest.modules.patrol.observationType.dto.ObservationTypeTranslationDTO;
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
public class ObservationTypeService {

    private final ObservationTypeRepository observationTypeRepository;
    private final ObservationTypeMapper observationTypeMapper;
    private final ObservationTypeTranslationValidator translationValidator;


    @Transactional(readOnly = true)
    public Page<ObservationTypeDTO> getAll(Pageable pageable) {

        return observationTypeRepository.findAll(pageable)
                .map(observationTypeMapper::toDTO);
    }


    @Transactional(readOnly = true)
    public ObservationTypeDTO getById(Long id) {

        return observationTypeMapper.toDTO(
                getObservationTypeOrThrow(id)
        );
    }


    public ObservationTypeDTO create(ObservationTypeDTO dto) {

        validateTranslations(dto.getTranslations());

        translationValidator.validateUniqueTranslationsForCreate(
                dto.getTranslations()
        );

        ObservationType observationType = new ObservationType();

        saveTranslations(
                observationType,
                dto.getTranslations()
        );

        translationValidator.ensureFrenchTranslation(observationType);

        ObservationType savedObservationType =
                observationTypeRepository.save(observationType);

        return observationTypeMapper.toDTO(savedObservationType);
    }


    public ObservationTypeDTO update(
            Long id,
            ObservationTypeDTO dto
    ) {
        ObservationType observationType = getObservationTypeOrThrow(id);

        validateTranslations(dto.getTranslations());

        translationValidator.validateUniqueTranslationsForUpdate(
                observationType,
                dto.getTranslations(),
                id
        );

        saveTranslations(
                observationType,
                dto.getTranslations()
        );

        translationValidator.ensureFrenchTranslation(observationType);

        observationTypeRepository.flush();

        return observationTypeMapper.toDTO(observationType);
    }


    public void delete(Long id) {

        ObservationType observationType = getObservationTypeOrThrow(id);

        observationTypeRepository.delete(observationType);
    }


    @Transactional(readOnly = true)
    public SearchResult<ObservationTypeDTO> search(
            ObservationTypeSearchQuery request
    ) {
        return SearchSpecification
                .filterBy(observationTypeRepository, request)
                .map(observationTypeMapper::toDTO);
    }


    private ObservationType getObservationTypeOrThrow(Long id) {

        return ServiceUtils.getOrThrow(
                () -> observationTypeRepository.findById(id),
                ErrorCode.OBSERVATION_TYPE_NOT_FOUND,
                "ObservationType not found with id: " + id
        );
    }

    private void validateTranslations(
            List<ObservationTypeTranslationDTO> translationDTOs
    ) {
        if (translationDTOs.isEmpty()) {
            throw new IllegalArgumentException(
                    "Au moins une traduction est obligatoire."
            );
        }
    }

    private void saveTranslations(
            ObservationType observationType,
            List<ObservationTypeTranslationDTO> translationDTOs
    ) {
        for (ObservationTypeTranslationDTO dto : translationDTOs) {

            if (dto.getName().isBlank()) {
                continue;
            }

            ObservationTypeTranslation translation =
                    observationType.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (translation == null) {

                translation = observationTypeMapper.toTranslation(
                        observationType,
                        dto
                );

                observationType.getTranslations().add(translation);

            } else {

                translation.setName(dto.getName().trim());
            }
        }
    }
}