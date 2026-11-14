package org.ong.dryforest.modules.patrol.incidentType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.patrol.incidentType.dto.IncidentTypeDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/incident-types")
@RequiredArgsConstructor
public class IncidentTypeController {

    private final IncidentTypeService incidentTypeService;

    @GetMapping
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public Page<IncidentTypeDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return incidentTypeService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public IncidentTypeDTO getById(@PathVariable Long id) {

        return incidentTypeService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('REFERENCE_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public IncidentTypeDTO create(
            @RequestBody IncidentTypeDTO dto
    ) {

        return incidentTypeService.create(dto);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public SearchResult<IncidentTypeDTO> search(
            @RequestBody IncidentTypeSearchQuery request
    ) {

        return incidentTypeService.search(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_UPDATE')")
    public IncidentTypeDTO update(
            @PathVariable Long id,
            @RequestBody IncidentTypeDTO dto
    ) {

        return incidentTypeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        incidentTypeService.delete(id);
    }
}
