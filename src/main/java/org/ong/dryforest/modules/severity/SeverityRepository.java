
package org.ong.dryforest.modules.severity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeverityRepository
        extends JpaRepository<Severity, Long>,
        JpaSpecificationExecutor<Severity> {

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM SeverityTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
        """)
    boolean existsDuplicateTranslation(
            @Param("name") String name
    );

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
        FROM SeverityTranslation t
        WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
          AND t.severity.id <> :severityId
        """)
    boolean existsDuplicateTranslationForUpdate(
            @Param("name") String name,
            @Param("severityId") Long severityId
    );
}
