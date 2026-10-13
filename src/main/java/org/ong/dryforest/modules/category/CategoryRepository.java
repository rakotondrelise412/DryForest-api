package org.ong.dryforest.modules.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository
        extends JpaRepository<Category, Long>,
        JpaSpecificationExecutor<Category> {


    @Query("""
    SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
    FROM CategoryTranslation t
    WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
""")
    boolean existsDuplicateTranslation(
            @Param("name") String name
    );

    @Query("""
    SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END
    FROM CategoryTranslation t
    WHERE LOWER(TRIM(t.name)) = LOWER(TRIM(:name))
      AND t.category.id <> :categoryId
""")
    boolean existsDuplicateTranslationForUpdate(
            @Param("name") String name,
            @Param("categoryId") Long categoryId
    );

}
