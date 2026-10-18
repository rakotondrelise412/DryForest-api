
package org.ong.dryforest.modules.zone.zoneNeed;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.Zone;
import org.ong.dryforest.modules.zone.ZoneRepository;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.search.SearchResult;
import org.ong.dryforest.shared.search.SearchSpecification;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZoneNeedService {

    private final ZoneNeedRepository zoneNeedRepository;
    private final ZoneRepository zoneRepository;
    private final ZoneNeedMapper mapper;

    @Transactional
    public ZoneNeedDTO create(ZoneNeedDTO dto) {
        ZoneNeed entity = mapper.toEntity(dto);

        entity.setUuid(UUID.randomUUID());
        entity.setZone(getZoneOrThrow(dto.getZoneId()));

        return mapper.toDTO(zoneNeedRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public Page<ZoneNeedDTO> findAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return zoneNeedRepository
                .findAllByIsDeletedFalse(pageable)
                .map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public SearchResult<ZoneNeedDTO> search(
            ZoneNeedSearchQuery query) {

        return SearchSpecification
                .filterBy(zoneNeedRepository, query)
                .map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ZoneNeedDTO findByUuid(UUID uuid) {
        return mapper.toDTO(getZoneNeedOrThrow(uuid));
    }

    @Transactional
    public ZoneNeedDTO update(UUID uuid, ZoneNeedDTO dto) {
        ZoneNeed entity = getZoneNeedOrThrow(uuid);

        entity.setIsSynced(dto.getIsSynced());
        entity.setZone(getZoneOrThrow(dto.getZoneId()));

        return mapper.toDTO(zoneNeedRepository.save(entity));
    }

    @Transactional
    public void delete(UUID uuid) {
        ZoneNeed entity = getZoneNeedOrThrow(uuid);

        entity.setDeleted(true);
        zoneNeedRepository.save(entity);
    }

    private Zone getZoneOrThrow(Long zoneId) {
        return ServiceUtils.getOrThrow(
                () -> zoneRepository.findById(zoneId),
                ErrorCode.ZONE_NOT_FOUND,
                "Zone introuvable"
        );
    }

    private ZoneNeed getZoneNeedOrThrow(UUID uuid) {
        return ServiceUtils.getOrThrow(
                () -> zoneNeedRepository
                        .findByUuidAndIsDeletedFalse(uuid),
                ErrorCode.ZONE_NEED_NOT_FOUND,
                "Zone need introuvable"
        );
    }
}
