package org.ong.dryforest.modules.patrol;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.patrol.dto.PatrolGroupDTO;
import org.ong.dryforest.modules.patrol.dto.PatrolGroupTranslationDTO;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PatrolGroupMapper {

    public PatrolGroupDTO toDTO(PatrolGroup patrolGroup) {

        var id = ServiceUtils.requireId(
                patrolGroup.getId(),
                "PatrolGroup ID must not be null"
        );

        var translations = patrolGroup.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        var dto = new PatrolGroupDTO(id, translations);

        dto.setCreatedAt(patrolGroup.getCreatedAt());
        dto.setUpdatedAt(patrolGroup.getUpdatedAt());

        return dto;
    }

    public PatrolGroupTranslation toTranslation(
            PatrolGroup patrolGroup,
            PatrolGroupTranslationDTO translationDTO
    ) {
        var translation = new PatrolGroupTranslation();

        translation.setLocale(translationDTO.getLocale());
        translation.setName(translationDTO.getName().trim());
        translation.setPatrolGroup(patrolGroup);

        return translation;
    }

    private PatrolGroupTranslationDTO translationToDTO(
            PatrolGroupTranslation translation
    ) {
        var id = ServiceUtils.requireId(
                translation.getId(),
                "PatrolGroupTranslation ID must not be null"
        );

        var dto = new PatrolGroupTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );

        dto.setCreatedAt(translation.getCreatedAt());
        dto.setUpdatedAt(translation.getUpdatedAt());

        return dto;
    }
}
