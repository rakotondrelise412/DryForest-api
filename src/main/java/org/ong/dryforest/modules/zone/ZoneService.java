package org.ong.dryforest.modules.zone;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.modules.site.SiteRepository;
import org.ong.dryforest.modules.zone.dto.ZoneTranslationDTO;
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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ZoneService {

    private final ZoneRepository zoneRepository;
    private final ZoneTypeRepository zoneTypeRepository;
    private final SiteRepository siteRepository;
    private final ZoneMapper zoneMapper;
    private final ZoneTranslationValidator translationValidator;

    @Transactional(readOnly = true)
    public Page<ZoneDTO> getAll(Pageable pageable) {

        return zoneRepository
                .findAllByIsDeletedFalse(pageable)
                .map(zoneMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ZoneDTO getByUuid(UUID uuid) {

        return zoneMapper.toDTO(
                getZoneOrThrow(uuid)
        );
    }

    public ZoneDTO create(ZoneDTO dto) {

        translationValidator
                .validateUniqueTranslationsForCreate(
                        dto.getTranslations()
                );

        Zone zone = zoneMapper.toEntity(dto);

        if (zone.getUuid() == null) {
            zone.setUuid(UUID.randomUUID());
        }

        ZoneType typeZone =
                ServiceUtils.getOrThrow(
                        () -> zoneTypeRepository.findById(
                                dto.getTypeZoneId()
                        ),
                        ErrorCode.ZONE_TYPE_NOT_FOUND,
                        "Type de zone introuvable"
                );

        zone.setTypeZone(typeZone);

        Site site =
                ServiceUtils.getOrThrow(
                        () -> siteRepository.findById(
                                dto.getSiteId()
                        ),
                        ErrorCode.SITE_NOT_FOUND,
                        "Site introuvable"
                );

        zone.setSite(site);

        saveTranslations(
                zone,
                dto.getTranslations()
        );

        Zone savedZone =
                zoneRepository.save(zone);

        return zoneMapper.toDTO(savedZone);
    }

    public ZoneDTO update(
            UUID uuid,
            ZoneDTO dto
    ) {

        Zone zone = getZoneOrThrow(uuid);

        Long zoneId = ServiceUtils.requireId(
                zone.getId(),
                "Zone ID must not be null"
        );

        translationValidator
                .validateUniqueTranslationsForUpdate(
                        zone,
                        dto.getTranslations(),
                        zoneId
                );

        zone.setArea(dto.getArea());

        zone.setIsSynced(dto.getIsSynced());

        zone.setGeom(
                zoneMapper.polygonToEntity(
                        dto.getGeom()
                )
        );

        ZoneType typeZone =
                ServiceUtils.getOrThrow(
                        () -> zoneTypeRepository.findById(
                                dto.getTypeZoneId()
                        ),
                        ErrorCode.ZONE_TYPE_NOT_FOUND,
                        "Type de zone introuvable"
                );

        zone.setTypeZone(typeZone);

        Site site =
                ServiceUtils.getOrThrow(
                        () -> siteRepository.findById(
                                dto.getSiteId()
                        ),
                        ErrorCode.SITE_NOT_FOUND,
                        "Site introuvable"
                );

        zone.setSite(site);

        saveTranslations(
                zone,
                dto.getTranslations()
        );

        zoneRepository.flush();

        return zoneMapper.toDTO(zone);
    }

    public void delete(UUID uuid) {

        Zone zone = getZoneOrThrow(uuid);

        zone.setDeleted(true);

        zoneRepository.save(zone);
    }

    @Transactional(readOnly = true)
    public SearchResult<ZoneDTO> search(
            ZoneSearchQuery request
    ) {

        return SearchSpecification
                .filterBy(zoneRepository, request)
                .map(zoneMapper::toDTO);
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
            List<ZoneTranslationDTO> translationDTOs
    ) {

        for (ZoneTranslationDTO dto : translationDTOs) {

            // Rechercher une traduction existante pour cette langue
            ZoneTranslation existing = zone.getTranslations()
                    .stream()
                    .filter(translation ->
                            translation.getLocale() == dto.getLocale()
                    )
                    .findFirst()
                    .orElse(null);

            if (existing != null) {
                existing.setName(dto.getName().trim());

            } else {
                ZoneTranslation translation =
                        zoneMapper.toTranslation(zone, dto);

                zone.getTranslations().add(translation);
            }
        }
    }
}