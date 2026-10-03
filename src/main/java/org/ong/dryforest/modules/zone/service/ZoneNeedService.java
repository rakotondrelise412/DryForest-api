package org.ong.dryforest.modules.zone.service;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.Zone;
import org.ong.dryforest.modules.zone.ZoneMapper;
import org.ong.dryforest.modules.zone.ZoneNeed;
import org.ong.dryforest.modules.zone.dto.ZoneNeedDTO;
import org.ong.dryforest.modules.zone.repository.ZoneNeedRepository;
import org.ong.dryforest.modules.zone.repository.ZoneRepository;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZoneNeedService {

    private final ZoneNeedRepository repository;

    private final ZoneRepository zoneRepository;

    private final ZoneMapper mapper;


    @Transactional
    public ZoneNeedDTO create(
            ZoneNeedDTO dto) {

        ZoneNeed entity =
                mapper.toEntity(dto);

        if (entity.getUuid() == null) {
            entity.setUuid(UUID.randomUUID());
        }

        if (entity.getIsSynced() == null) {
            entity.setIsSynced(false);
        }

        if (dto.getZoneId() != null) {

            Zone zone =
                    ServiceUtils.getOrThrow(
                            () -> zoneRepository.findById(
                                    dto.getZoneId()
                            ),
                            ErrorCode.ZONE_NOT_FOUND,
                            "Zone introuvable"
                    );

            entity.setZone(zone);
        }

        return mapper.toDTO(
                repository.save(entity)
        );
    }


    @Transactional(readOnly = true)
    public Page<ZoneNeedDTO> findAll(
            Pageable pageable) {

        return repository
                .findAllByIsDeletedFalse(pageable)
                .map(mapper::toDTO);
    }


    @Transactional(readOnly = true)
    public ZoneNeedDTO findByUuid(
            UUID uuid) {

        ZoneNeed entity =
                ServiceUtils.getOrThrow(
                        () -> repository
                                .findByUuidAndIsDeletedFalse(uuid),
                        ErrorCode.ZONE_NEED_NOT_FOUND,
                        "Besoin de zone introuvable"
                );

        return mapper.toDTO(entity);
    }


    @Transactional
    public ZoneNeedDTO update(
            UUID uuid,
            ZoneNeedDTO dto) {

        ZoneNeed entity =
                ServiceUtils.getOrThrow(
                        () -> repository
                                .findByUuidAndIsDeletedFalse(uuid),
                        ErrorCode.ZONE_NEED_NOT_FOUND,
                        "Besoin de zone introuvable"
                );

        if (dto.getIsSynced() != null) {
            entity.setIsSynced(
                    dto.getIsSynced()
            );
        }

        if (dto.getZoneId() != null) {

            Zone zone =
                    ServiceUtils.getOrThrow(
                            () -> zoneRepository.findById(
                                    dto.getZoneId()
                            ),
                            ErrorCode.ZONE_NOT_FOUND,
                            "Zone introuvable"
                    );

            entity.setZone(zone);
        }

        return mapper.toDTO(
                repository.save(entity)
        );
    }


    @Transactional
    public void delete(UUID uuid) {

        ZoneNeed entity =
                ServiceUtils.getOrThrow(
                        () -> repository
                                .findByUuidAndIsDeletedFalse(uuid),
                        ErrorCode.ZONE_NEED_NOT_FOUND,
                        "Besoin de zone introuvable"
                );

        entity.setDeleted(true);

        repository.save(entity);
    }
}