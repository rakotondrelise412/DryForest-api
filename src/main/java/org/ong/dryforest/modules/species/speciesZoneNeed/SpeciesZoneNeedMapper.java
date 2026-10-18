package org.ong.dryforest.modules.species.speciesZoneNeed;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.species.Species;
import org.ong.dryforest.modules.species.SpeciesRepository;
import org.ong.dryforest.modules.zone.zoneNeed.ZoneNeed;
import org.ong.dryforest.modules.zone.zoneNeed.ZoneNeedRepository;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpeciesZoneNeedMapper {

    private final SpeciesZoneNeedRepository repository;
    private final SpeciesRepository speciesRepository;
    private final ZoneNeedRepository zoneNeedRepository;

    public SpeciesZoneNeedDTO toDTO(SpeciesZoneNeed entity) {

        var id = ServiceUtils.requireId(
                entity.getId(),
                "SpeciesZoneNeed ID must not be null"
        );

        var speciesId = ServiceUtils.requireId(
                entity.getSpecies().getId(),
                "Species ID must not be null"
        );

        var zoneNeedId = ServiceUtils.requireId(
                entity.getZoneNeed().getId(),
                "ZoneNeed ID must not be null"
        );

        return new SpeciesZoneNeedDTO(
                id,
                entity.getUuid(),
                speciesId,
                zoneNeedId,
                entity.getIsSynced()
        );
    }


    public SpeciesZoneNeed toEntity(SpeciesZoneNeedDTO dto) {

        SpeciesZoneNeed entity = repository.findById(dto.getId())
        .orElseThrow(() -> new IllegalArgumentException(
                "SpeciesZoneNeed introuvable : " + dto.getId()
        ));

        entity.setUuid(dto.getUuid());
        entity.setIsSynced(
                dto.getIsSynced()
        );

        Species species = ServiceUtils.getOrThrow(
                () -> speciesRepository.findById(
                        ServiceUtils.requireId(
                                dto.getIdSpecies(),
                                "Species ID must not be null"
                        )
                ),
                ErrorCode.INTERNAL_ERROR,
                "Species introuvable : " + dto.getIdSpecies()
        );

        entity.setSpecies(species);

        ZoneNeed zoneNeed = ServiceUtils.getOrThrow(
                () -> zoneNeedRepository.findById(
                        ServiceUtils.requireId(
                                dto.getIdZoneNeed(),
                                "ZoneNeed ID must not be null"
                        )
                ),
                ErrorCode.INTERNAL_ERROR,
                "ZoneNeed introuvable : " + dto.getIdZoneNeed()
        );

        entity.setZoneNeed(zoneNeed);

        return entity;
    }

}
