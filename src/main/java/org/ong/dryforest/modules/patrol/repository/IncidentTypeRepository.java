package org.ong.dryforest.modules.patrol.repository;


import org.ong.dryforest.modules.patrol.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentTypeRepository extends JpaRepository<IncidentType, Long> {
}
