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
        SpeciesType speciesType = new SpeciesType();

        saveTranslations(
                speciesType,
                speciesTypeDTO.getTranslations()
        );

        SpeciesType savedSpeciesType =
                speciesTypeRepository.save(speciesType);

        return speciesTypeMapper.toDTO(savedSpeciesType);
    }

    public SpeciesTypeDTO update(
            Long id,
            SpeciesTypeDTO speciesTypeDTO
    ) {
        SpeciesType speciesType = getSpeciesTypeOrThrow(id);

        speciesType.getTranslations().clear();

        speciesTypeRepository.flush();

        saveTranslations(
                speciesType,
                speciesTypeDTO.getTranslations()
        );

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

        translationDTOs.stream()
                .map(dto -> speciesTypeMapper.toTranslation(
                        speciesType,
                        dto
                ))
                .forEach(speciesType.getTranslations()::add);
    }
}

