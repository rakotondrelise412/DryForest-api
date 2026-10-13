package org.ong.dryforest.modules.species.speciesType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.species.speciesType.dto.SpeciesTypeDTO;
import org.ong.dryforest.modules.species.speciesType.dto.SpeciesTypeTranslationDTO;
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
public class SpeciesTypeService {

    private final SpeciesTypeRepository speciesTypeRepository;
    private final SpeciesTypeMapper speciesTypeMapper;
    private final SpeciesTypeTranslationValidator translationValidator;

    @Transactional(readOnly = true)
    public Page<SpeciesTypeDTO> getAll(Pageable pageable) {
        return speciesTypeRepository.findAll(pageable)
                .map(speciesTypeMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public SpeciesTypeDTO getById(Long id) {
        return speciesTypeMapper.toDTO(getSpeciesTypeOrThrow(id));
    }


    public SpeciesTypeDTO create(SpeciesTypeDTO speciesTypeDTO) {
        translationValidator.validateUniqueTranslationsForCreate(
                speciesTypeDTO.getTranslations()
        );

        SpeciesType speciesType = new SpeciesType();

        saveTranslations(
                speciesType,
                speciesTypeDTO.getTranslations()
        );

        translationValidator.ensureFrenchTranslation(speciesType);

        SpeciesType savedSpeciesType =
                speciesTypeRepository.save(speciesType);

        return speciesTypeMapper.toDTO(savedSpeciesType);
    }



    public SpeciesTypeDTO update(
            Long id,
            SpeciesTypeDTO speciesTypeDTO
    ) {
        SpeciesType speciesType = getSpeciesTypeOrThrow(id);

        translationValidator.validateUniqueTranslationsForUpdate(
                speciesType,
                speciesTypeDTO.getTranslations(),
                id
        );

        saveTranslations(
                speciesType,
                speciesTypeDTO.getTranslations()
        );

        translationValidator.ensureFrenchTranslation(speciesType);

        speciesTypeRepository.flush();

        return speciesTypeMapper.toDTO(speciesType);
    }


    public void delete(Long id) {
        SpeciesType speciesType = getSpeciesTypeOrThrow(id);
        speciesTypeRepository.delete(speciesType);
    }

    @Transactional(readOnly = true)
    public SearchResult<SpeciesTypeDTO> search(
            SpeciesTypeSearchQuery request
    ) {
        return SearchSpecification
                .filterBy(speciesTypeRepository, request)
                .map(speciesTypeMapper::toDTO);
    }

    private SpeciesType getSpeciesTypeOrThrow(Long id) {
        return ServiceUtils.getOrThrow(
                () -> speciesTypeRepository.findById(id),
                ErrorCode.SPECIES_TYPE_NOT_FOUND,
                "SpeciesType not found with id: " + id
        );
    }



    private void saveTranslations(
            SpeciesType speciesType,
            List<SpeciesTypeTranslationDTO> translationDTOs
    ) {
        for (SpeciesTypeTranslationDTO dto : translationDTOs) {

            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            SpeciesTypeTranslation translation =
                    speciesType.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (translation == null) {
                translation = speciesTypeMapper.toTranslation(
                        speciesType,
                        dto
                );

                translation.setName(name);
                speciesType.getTranslations().add(translation);
            } else {
                translation.setName(name);
            }
        }
    }

}


