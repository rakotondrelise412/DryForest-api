package org.ong.dryforest.modules.zone.translations;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZoneTranslationRepository
        extends JpaRepository<ZoneTranslation, Long> {

    List<ZoneTranslation> findByZoneId(Long zoneId);
}
