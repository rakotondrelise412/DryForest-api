package org.ong.dryforest.modules.zone.translations;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneTypeTranslationRepository
        extends JpaRepository<ZoneTypeTranslation, Long> {

    List<ZoneTypeTranslation> findByZoneTypeId(Long zoneTypeId);
}