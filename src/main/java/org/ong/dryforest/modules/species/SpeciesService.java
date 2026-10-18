package org.ong.dryforest.modules.species;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.species.dto.SpeciesDTO;
import org.ong.dryforest.modules.species.dto.SpeciesTranslationDTO;
import org.ong.dryforest.modules.species.speciesType.SpeciesType;
import org.ong.dryforest.modules.species.speciesType.SpeciesTypeRepository;
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
public class SpeciesService {

    private final SpeciesRepository speciesRepository;
    private final SpeciesTypeRepository speciesTypeRepository;
    private final SpeciesMapper speciesMapper;
    private final SpeciesTranslationValidator translationValidator;

    @Transactional(readOnly = true)
    public Page<SpeciesDTO> getAll(Pageable pageable) {
        return speciesRepository.findAll(pageable)
                .map(speciesMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public SpeciesDTO getById(Long id) {
        return speciesMapper.toDTO(getSpeciesOrThrow(id));
    }

    public SpeciesDTO create(SpeciesDTO dto) {
        List<SpeciesTranslationDTO> translations = dto.getTranslations();

        translationValidator.validateUniqueTranslationsForCreate(
                translations
        );

        Species species = new Species();
        species.setScientificName(dto.getScientificName());
        species.setDensity(dto.getDensity());
        species.setSpeciesType(
                getSpeciesTypeOrNull(dto.getSpeciesTypeId())
        );

        saveTranslations(species, translations);
        translationValidator.ensureFrenchTranslation(species);

        Species savedSpecies = speciesRepository.save(species);

        return speciesMapper.toDTO(savedSpecies);
    }

    public SpeciesDTO update(Long id, SpeciesDTO dto) {
        Species species = getSpeciesOrThrow(id);
        List<SpeciesTranslationDTO> translations = dto.getTranslations();

        translationValidator.validateUniqueTranslationsForUpdate(
                species,
                translations,
                id
        );

        species.setScientificName(dto.getScientificName());
        species.setDensity(dto.getDensity());
        species.setSpeciesType(
                getSpeciesTypeOrNull(dto.getSpeciesTypeId())
        );

        saveTranslations(species, translations);
        translationValidator.ensureFrenchTranslation(species);

        speciesRepository.flush();

        return speciesMapper.toDTO(species);
    }

    public void delete(Long id) {
        speciesRepository.delete(getSpeciesOrThrow(id));
    }

    @Transactional(readOnly = true)
    public SearchResult<SpeciesDTO> search(SpeciesSearchQuery request) {
        return SearchSpecification
                .filterBy(speciesRepository, request)
                .map(speciesMapper::toDTO);
    }

    private Species getSpeciesOrThrow(Long id) {
        return ServiceUtils.getOrThrow(
                () -> speciesRepository.findById(id),
                ErrorCode.SPECIES_NOT_FOUND,
                "Species not found with id: " + id
        );
    }

    @Nullable
    private SpeciesType getSpeciesTypeOrNull(@Nullable Long speciesTypeId) {
        if (speciesTypeId == null) {
            return null;
        }

        return ServiceUtils.getOrThrow(
                () -> speciesTypeRepository.findById(speciesTypeId),
                ErrorCode.SPECIES_TYPE_NOT_FOUND,
                "SpeciesType not found with id: " + speciesTypeId
        );
    }

    private void saveTranslations(
            Species species,
            List<SpeciesTranslationDTO> translationDTOs
    ) {
        for (SpeciesTranslationDTO dto : translationDTOs) {
            if (dto.getName().isBlank()) {
                continue;
            }

            String name = dto.getName().trim();

            var translation = species.getTranslations()
                    .stream()
                    .filter(t -> t.getLocale() == dto.getLocale())
                    .findFirst()
                    .orElse(null);

            if (translation == null) {
                translation = speciesMapper.toTranslation(species, dto);
                translation.setName(name);
                species.getTranslations().add(translation);
            } else {
                translation.setName(name);
            }
        }
    }
}
