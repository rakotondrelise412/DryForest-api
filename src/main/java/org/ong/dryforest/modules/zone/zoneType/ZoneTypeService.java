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

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ZoneTypeService {

    private final ZoneTypeRepository zoneTypeRepository;
    private final ZoneTypeMapper zoneTypeMapper;

    @Transactional(readOnly = true)
    public Page<ZoneTypeDTO> getAll(Pageable pageable) {
        return zoneTypeRepository
                .findAllByIsDeletedFalse(pageable)
                .map(zoneTypeMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ZoneTypeDTO getById(Long id) {
        return zoneTypeMapper.toDTO(
                getZoneTypeOrThrow(id)
        );
    }

    public ZoneTypeDTO create(ZoneTypeDTO dto) {

        ZoneType zoneType = zoneTypeMapper.toEntity(dto);

        saveTranslations(
                zoneType,
                dto.getTranslations()
        );

        ZoneType savedZoneType =
                zoneTypeRepository.save(zoneType);

        return zoneTypeMapper.toDTO(savedZoneType);
    }

    public ZoneTypeDTO update(
            Long id,
            ZoneTypeDTO dto
    ) {
        ZoneType zoneType = getZoneTypeOrThrow(id);

        zoneType.setName(dto.getName());

        zoneType.getTranslations().clear();

        // Supprime les anciennes traductions avant
        // d'en ajouter de nouvelles.
        zoneTypeRepository.flush();

        saveTranslations(
                zoneType,
                dto.getTranslations()
        );

        zoneTypeRepository.flush();

        return zoneTypeMapper.toDTO(zoneType);
    }

    public void delete(Long id) {
        ZoneType zoneType = getZoneTypeOrThrow(id);

        zoneType.setDeleted(true);

        zoneTypeRepository.save(zoneType);
    }


    @Transactional(readOnly = true)
    public SearchResult<ZoneTypeDTO> search(
            ZoneTypeSearchQuery request
    ) {
        if (!request.getName().isBlank()) {

            request.setTranslationName(request.getName());
        }

        return SearchSpecification
                .filterBy(zoneTypeRepository, request)
                .map(zoneTypeMapper::toDTO);
    }


    private ZoneType getZoneTypeOrThrow(Long id) {
        return ServiceUtils.getOrThrow(
                () -> zoneTypeRepository
                        .findByIdAndIsDeletedFalse(id),
                ErrorCode.ZONE_TYPE_NOT_FOUND,
                "ZoneType not found with id: " + id
        );
    }

    private void saveTranslations(
            ZoneType zoneType,
            List<ZoneTypeTranslationDTO> translationDTOs
    ) {

        translationDTOs.stream()
                .map(dto -> zoneTypeMapper.toTranslation(
                        zoneType,
                        dto
                ))
                .forEach(zoneType.getTranslations()::add);
    }
}
