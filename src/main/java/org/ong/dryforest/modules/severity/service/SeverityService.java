package org.ong.dryforest.modules.severity.service;

import org.ong.dryforest.modules.severity.dto.SeverityDTO;

import java.util.List;

public interface SeverityService {

    List<SeverityDTO> getAll();

    SeverityDTO getById(Long id);

    SeverityDTO create(SeverityDTO severityDTO);

    SeverityDTO update(Long id, SeverityDTO severityDTO);

    void delete(Long id);
}