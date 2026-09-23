package org.ong.dryforest.modules.severity.translations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeverityTranslationRepository
        extends JpaRepository<SeverityTranslation, Long> {

    List<SeverityTranslation> FindAllBySeverity_Id(Long severityId);
}