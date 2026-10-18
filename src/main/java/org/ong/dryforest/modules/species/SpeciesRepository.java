
package org.ong.dryforest.modules.species;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesRepository
        extends JpaRepository<Species, Long>,
        JpaSpecificationExecutor<Species> {

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM SpeciesTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
    """)
    boolean existsDuplicateTranslation(
            @Param("name") String name
    );

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM SpeciesTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
          AND t.species.id <> :speciesId
    """)
    boolean existsDuplicateTranslationForUpdate(
            @Param("name") String name,
            @Param("speciesId") Long speciesId
    );
}
