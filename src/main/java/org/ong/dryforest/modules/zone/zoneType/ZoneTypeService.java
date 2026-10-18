package org.ong.dryforest.modules.zone.zoneType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.zoneType.dto.ZoneTypeDTO;
import org.ong.dryforest.modules.zone.zoneType.dto.ZoneTypeTranslationDTO;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.search.SearchResult;
import org.ong.dryforest.shared.search.SearchSpecification;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ZoneTypeService {

    private final ZoneTypeRepository zoneTypeRepository;
    private final ZoneTypeMapper zoneTypeMapper;
    private final ZoneTypeTranslationValidator translationValidator;

    @Transactional(readOnly = true)
    public Page<ZoneTypeDTO> getAll(Pageable pageable) {
        return zoneTypeRepository.findAllByIsDeletedFalse(pageable)
                .map(zoneTypeMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ZoneTypeDTO getById(Long id) {
        return zoneTypeMapper.toDTO(getZoneTypeOrThrow(id));
    }

    public ZoneTypeDTO create(ZoneTypeDTO dto) {
        List<ZoneTypeTranslationDTO> translations = dto.getTranslations();

        translationValidator.validateForCreate(translations);

        ZoneType zoneType = zoneTypeMapper.toEntity(dto);
        saveTranslations(zoneType, translations);

        return zoneTypeMapper.toDTO(zoneTypeRepository.save(zoneType));
    }

    public ZoneTypeDTO update(Long id, ZoneTypeDTO dto) {
        ZoneType zoneType = getZoneTypeOrThrow(id);
        List<ZoneTypeTranslationDTO> translations = dto.getTranslations();

        translationValidator.validateForUpdate(id, translations);

        zoneType.setName(dto.getName());
        saveTranslations(zoneType, translations);

        return zoneTypeMapper.toDTO(zoneTypeRepository.save(zoneType));
    }

    public void delete(Long id) {
        ZoneType zoneType = getZoneTypeOrThrow(id);
        zoneType.setDeleted(true);
        zoneTypeRepository.save(zoneType);
    }

    @Transactional(readOnly = true)
    public SearchResult<ZoneTypeDTO> search(ZoneTypeSearchQuery request) {
        if (StringUtils.hasText(request.getName())) {
            request.setTranslationName(request.getName());
        }

        return SearchSpecification
                .filterBy(zoneTypeRepository, request)
                .map(zoneTypeMapper::toDTO);
    }

    private ZoneType getZoneTypeOrThrow(Long id) {
        return ServiceUtils.getOrThrow(
                () -> zoneTypeRepository.findByIdAndIsDeletedFalse(id),
                ErrorCode.ZONE_TYPE_NOT_FOUND,
                "ZoneType not found with id: " + id
        );
    }

    private void saveTranslations(
            ZoneType zoneType,
            List<ZoneTypeTranslationDTO> translationDTOs
    ) {
        for (ZoneTypeTranslationDTO dto : translationDTOs) {
            if (!StringUtils.hasText(dto.getName())) {
                continue;
            }

            var existing = zoneType.getTranslations()
                    .stream()
                    .filter(translation ->
                            translation.getLocale() == dto.getLocale()
                    )
                    .findFirst()
                    .orElse(null);

            if (existing == null) {
                zoneType.getTranslations().add(
                        zoneTypeMapper.toTranslation(zoneType, dto)
                );
            } else {
                existing.setName(dto.getName().trim());
            }
        }
    }
}