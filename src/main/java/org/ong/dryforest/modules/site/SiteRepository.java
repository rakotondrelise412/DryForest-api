package org.ong.dryforest.modules.site;

import org.ong.dryforest.enums.Locale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SiteRepository
        extends JpaRepository<Site, Long>,
        JpaSpecificationExecutor<Site> {

    Optional<Site> findByIdAndIsDeletedFalse(Long id);

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM SiteTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
          AND t.locale = :locale
    """)
    boolean existsDuplicateTranslation(
            @Param("name") String name,
            @Param("locale") Locale locale
    );

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM SiteTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
          AND t.locale = :locale
          AND t.site.id <> :siteId
    """)
    boolean existsDuplicateTranslationForUpdate(
            @Param("name") String name,
            @Param("locale") Locale locale,
            @Param("siteId") Long siteId
    );
}
