package org.ong.dryforest.modules.species.speciesType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeciesTypeRepository
        extends JpaRepository<SpeciesType, Long>,
        JpaSpecificationExecutor<SpeciesType> {
}

