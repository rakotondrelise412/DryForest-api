package org.ong.dryforest.modules.category.translations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryTranslationRepository extends JpaRepository<CategoryTranslation, Long> {

    List<CategoryTranslation> findByCategory_id(Long CategoryId);
}
