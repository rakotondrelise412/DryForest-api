package org.ong.dryforest.modules.site.translation;

import org.ong.dryforest.enums.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SiteTranslationRepository
        extends JpaRepository<SiteTranslation, Long> {

    List<SiteTranslation> findAllBySite_Id(Long siteId);

    Optional<SiteTranslation> findBySite_IdAndLocale(
            Long siteId,
            Locale locale
    );
}