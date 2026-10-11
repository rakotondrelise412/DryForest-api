package org.ong.dryforest.modules.species.speciesZoneNeed;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpeciesZoneNeedRepository
        extends JpaRepository<SpeciesZoneNeed, Long> {

    Optional<SpeciesZoneNeed> findByUuid(UUID uuid);

    boolean existsByUuid(UUID uuid);

    List<SpeciesZoneNeed> findAllBySpecies_Id(Long speciesId);

    List<SpeciesZoneNeed> findAllByZoneNeed_Id(Long zoneNeedId);

    List<SpeciesZoneNeed> findAllBySpecies_IdAndZoneNeed_Id(
            Long speciesId,
            Long zoneNeedId
    );
}

