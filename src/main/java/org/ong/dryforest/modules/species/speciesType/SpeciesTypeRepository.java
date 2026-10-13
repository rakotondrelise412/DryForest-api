package org.ong.dryforest.modules.species.speciesType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SpeciesTypeRepository
        extends JpaRepository<SpeciesType, Long>,
        JpaSpecificationExecutor<SpeciesType> {

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM SpeciesTypeTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
    """)
    boolean existsDuplicateTranslation(
            @Param("name") String name
    );

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM SpeciesTypeTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
          AND t.speciesType.id <> :speciesTypeId
    """)
    boolean existsDuplicateTranslationForUpdate(
            @Param("name") String name,
            @Param("speciesTypeId") Long speciesTypeId
    );
}


