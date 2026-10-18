package org.ong.dryforest.modules.zone;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.modules.site.SiteRepository;
import org.ong.dryforest.modules.zone.zoneType.ZoneType;
import org.ong.dryforest.modules.zone.dto.ZoneDTO;
import org.ong.dryforest.modules.zone.zoneType.ZoneTypeRepository;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.search.SearchResult;
import org.ong.dryforest.shared.search.SearchSpecification;
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

        Zone zone = zoneMapper.toEntity(dto);

        if (zone.getUuid() == null) {
            zone.setUuid(UUID.randomUUID());
        }

        ZoneType type =
                ServiceUtils.getOrThrow(
                        () -> zoneTypeRepository.findById(
                                dto.getTypeZoneId()
                        ),
                        ErrorCode.ZONE_TYPE_NOT_FOUND,
                        "Type de zone introuvable"
                );

        zone.setTypeZone(type);

        Site site =
                ServiceUtils.getOrThrow(
                        () -> siteRepository.findById(
                                dto.getSiteId()
                        ),
                        ErrorCode.SITE_NOT_FOUND,
                        "Site introuvable"
                );

        zone.setSite(site);

        saveTranslations(zone, dto);

        return zoneMapper.toDTO(
                zoneRepository.save(zone)
        );
    }


    @Transactional(readOnly = true)
    public Page<ZoneDTO> findAll(Pageable pageable) {

        return zoneRepository
                .findAllByIsDeletedFalse(pageable)
                .map(zoneMapper::toDTO);
    }


    @Transactional(readOnly = true)
    public ZoneDTO findByUuid(UUID uuid) {

        Zone zone = getZoneOrThrow(uuid);

        return zoneMapper.toDTO(zone);
    }


    @Transactional
    public ZoneDTO update(
            UUID uuid,
            ZoneDTO dto) {

        Zone zone = getZoneOrThrow(uuid);

        zone.setArea(dto.getArea());

        zone.setIsSynced(dto.getIsSynced());

        zone.setGeom(
                zoneMapper.polygonToEntity(
                        dto.getGeom()
                )
        );

        ZoneType type =
                ServiceUtils.getOrThrow(
                        () -> zoneTypeRepository.findById(
                                dto.getTypeZoneId()
                        ),
                        ErrorCode.ZONE_TYPE_NOT_FOUND,
                        "Type de zone introuvable"
                );

        zone.setTypeZone(type);

        Site site =
                ServiceUtils.getOrThrow(
                        () -> siteRepository.findById(
                                dto.getSiteId()
                        ),
                        ErrorCode.SITE_NOT_FOUND,
                        "Site introuvable"
                );

        zone.setSite(site);

        saveTranslations(zone, dto);

        return zoneMapper.toDTO(
                zoneRepository.save(zone)
        );
    }


    @Transactional
    public void delete(UUID uuid) {

        Zone zone = getZoneOrThrow(uuid);

        zone.setDeleted(true);

        zoneRepository.save(zone);
    }

    @Transactional(readOnly = true)
    public SearchResult<Zone> search(
            ZoneSearchQuery request) {

        return SearchSpecification.filterBy(
                zoneRepository,
                request
        );
    }

    private Zone getZoneOrThrow(UUID uuid) {

        return ServiceUtils.getOrThrow(
                () -> zoneRepository
                        .findByUuidAndIsDeletedFalse(uuid),
                ErrorCode.ZONE_NOT_FOUND,
                "Zone introuvable"
        );
    }


    private void saveTranslations(
            Zone zone,
            ZoneDTO dto) {

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
}