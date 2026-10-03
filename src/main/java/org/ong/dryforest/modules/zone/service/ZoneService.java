package org.ong.dryforest.modules.zone.service;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.modules.site.SiteRepository;
import org.ong.dryforest.modules.zone.Zone;
import org.ong.dryforest.modules.zone.ZoneMapper;
import org.ong.dryforest.modules.zone.ZoneType;
import org.ong.dryforest.modules.zone.dto.ZoneDTO;
import org.ong.dryforest.modules.zone.repository.ZoneRepository;
import org.ong.dryforest.modules.zone.repository.ZoneTypeRepository;
import org.ong.dryforest.modules.zone.translations.ZoneTranslation;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ZoneService {

    private final ZoneRepository zoneRepository;

    private final ZoneTypeRepository zoneTypeRepository;

    private final SiteRepository siteRepository;

    private final ZoneMapper zoneMapper;


    @Transactional
    public ZoneDTO create(ZoneDTO dto) {

        Zone zone =
                zoneMapper.toEntity(dto);

        if (zone.getUuid() == null) {
            zone.setUuid(UUID.randomUUID());
        }

        if (zone.getArea() == null) {
            zone.setArea(0.0);
        }

        if (zone.getIsSynced() == null) {
            zone.setIsSynced(false);
        }

        if (dto.getTypeZoneId() != null) {

            ZoneType type =
                    ServiceUtils.getOrThrow(
                            () -> zoneTypeRepository.findById(
                                    dto.getTypeZoneId()
                            ),
                            ErrorCode.ZONE_TYPE_NOT_FOUND,
                            "Type de zone introuvable"
                    );

            zone.setTypeZone(type);
        }

        if (dto.getSiteId() != null) {

            Site site =
                    ServiceUtils.getOrThrow(
                            () -> siteRepository.findById(
                                    dto.getSiteId()
                            ),
                            ErrorCode.SITE_NOT_FOUND,
                            "Site introuvable"
                    );

            zone.setSite(site);
        }

        if (dto.getTranslations() != null) {

            dto.getTranslations()
                    .forEach(translationDTO -> {

                        ZoneTranslation translation =
                                zoneMapper.toEntity(
                                        translationDTO
                                );

                        translation.setZone(zone);

                        zone.getTranslations()
                                .add(translation);
                    });
        }

        return zoneMapper.toDTO(
                zoneRepository.save(zone)
        );
    }


    @Transactional(readOnly = true)
    public Page<ZoneDTO> findAll(
            Pageable pageable) {

        return zoneRepository
                .findAllByIsDeletedFalse(pageable)
                .map(zoneMapper::toDTO);
    }


    @Transactional(readOnly = true)
    public ZoneDTO findByUuid(UUID uuid) {

        Zone zone =
                ServiceUtils.getOrThrow(
                        () -> zoneRepository
                                .findByUuidAndIsDeletedFalse(uuid),
                        ErrorCode.ZONE_NOT_FOUND,
                        "Zone introuvable"
                );

        return zoneMapper.toDTO(zone);
    }


    @Transactional
    public ZoneDTO update(
            UUID uuid,
            ZoneDTO dto) {

        Zone zone =
                ServiceUtils.getOrThrow(
                        () -> zoneRepository
                                .findByUuidAndIsDeletedFalse(uuid),
                        ErrorCode.ZONE_NOT_FOUND,
                        "Zone introuvable"
                );

        if (dto.getArea() != null) {
            zone.setArea(dto.getArea());
        }

        if (dto.getIsSynced() != null) {
            zone.setIsSynced(
                    dto.getIsSynced()
            );
        }

        if (dto.getGeom() != null) {
            zone.setGeom(
                    zoneMapper.polygonToEntity(
                            dto.getGeom()
                    )
            );
        }

        if (dto.getTypeZoneId() != null) {

            ZoneType type =
                    ServiceUtils.getOrThrow(
                            () -> zoneTypeRepository.findById(
                                    dto.getTypeZoneId()
                            ),
                            ErrorCode.ZONE_TYPE_NOT_FOUND,
                            "Type de zone introuvable"
                    );

            zone.setTypeZone(type);
        }

        if (dto.getSiteId() != null) {

            Site site =
                    ServiceUtils.getOrThrow(
                            () -> siteRepository.findById(
                                    dto.getSiteId()
                            ),
                            ErrorCode.SITE_NOT_FOUND,
                            "Site introuvable"
                    );

            zone.setSite(site);
        }

        if (dto.getTranslations() != null) {

            zone.getTranslations().clear();

            dto.getTranslations()
                    .forEach(translationDTO -> {

                        ZoneTranslation translation =
                                zoneMapper.toEntity(
                                        translationDTO
                                );

                        translation.setZone(zone);

                        zone.getTranslations()
                                .add(translation);
                    });
        }

        return zoneMapper.toDTO(
                zoneRepository.save(zone)
        );
    }


    @Transactional
    public void delete(UUID uuid) {

        Zone zone =
                ServiceUtils.getOrThrow(
                        () -> zoneRepository
                                .findByUuidAndIsDeletedFalse(uuid),
                        ErrorCode.ZONE_NOT_FOUND,
                        "Zone introuvable"
                );

        zone.setDeleted(true);

        zoneRepository.save(zone);
    }
}