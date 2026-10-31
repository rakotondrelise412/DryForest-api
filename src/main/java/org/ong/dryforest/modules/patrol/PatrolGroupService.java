package org.ong.dryforest.modules.patrol;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.patrol.dto.PatrolGroupDTO;
import org.ong.dryforest.modules.patrol.dto.PatrolGroupTranslationDTO;
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
public class PatrolGroupService {

    private final PatrolGroupRepository patrolGroupRepository;
    private final PatrolGroupMapper patrolGroupMapper;
    private final PatrolGroupTranslationValidator translationValidator;

    @Transactional(readOnly = true)
    public Page<PatrolGroupDTO> getAll(Pageable pageable) {

        return patrolGroupRepository.findAll(pageable)
                .map(patrolGroupMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public PatrolGroupDTO getById(Long id) {

        return patrolGroupMapper.toDTO(getPatrolGroupOrThrow(id));
    }

    public PatrolGroupDTO create(PatrolGroupDTO patrolGroupDTO) {

        translationValidator.validateUniqueTranslationsForCreate(
                patrolGroupDTO.getTranslations()
        );

        PatrolGroup patrolGroup = new PatrolGroup();

        saveTranslations(patrolGroup, patrolGroupDTO.getTranslations());

        translationValidator.ensureFrenchTranslation(patrolGroup);

        PatrolGroup savedPatrolGroup = patrolGroupRepository.save(patrolGroup);

        return patrolGroupMapper.toDTO(savedPatrolGroup);
    }

    public PatrolGroupDTO update(Long id, PatrolGroupDTO patrolGroupDTO) {

        PatrolGroup patrolGroup = getPatrolGroupOrThrow(id);

        translationValidator.validateUniqueTranslationsForUpdate(
                patrolGroup,
                patrolGroupDTO.getTranslations(),
                id
        );

        saveTranslations(patrolGroup, patrolGroupDTO.getTranslations());

        translationValidator.ensureFrenchTranslation(patrolGroup);

        patrolGroupRepository.flush();

        return patrolGroupMapper.toDTO(patrolGroup);
    }

    public void delete(Long id) {

        PatrolGroup patrolGroup = getPatrolGroupOrThrow(id);

        patrolGroupRepository.delete(patrolGroup);
    }

    @Transactional(readOnly = true)
    public SearchResult<PatrolGroupDTO> search(
            PatrolGroupSearchQuery request
    ) {
        return SearchSpecification
                .filterBy(patrolGroupRepository, request)
                .map(patrolGroupMapper::toDTO);
    }

    private PatrolGroup getPatrolGroupOrThrow(Long id) {

        return ServiceUtils.getOrThrow(
                () -> patrolGroupRepository.findById(id),
                ErrorCode.PATROL_GROUP_NOT_FOUND,
                "PatrolGroup not found with id: " + id
        );
    }

    private void saveTranslations(
            PatrolGroup patrolGroup,
            List<PatrolGroupTranslationDTO> translationDTOs
    ) {

        for (PatrolGroupTranslationDTO dto : translationDTOs) {

            if (dto.getName().isBlank()) {
                continue;
            }

            PatrolGroupTranslation translation =
                    patrolGroup.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (translation == null) {

                translation = patrolGroupMapper.toTranslation(
                        patrolGroup, dto
                );

                patrolGroup.getTranslations().add(translation);

            } else {

                translation.setName(dto.getName().trim());
            }
        }
    }
}
