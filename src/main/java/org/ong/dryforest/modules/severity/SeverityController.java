package org.ong.dryforest.modules.severity;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.severity.dto.SeverityDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/severities")
@RequiredArgsConstructor
public class SeverityController {

    private final SeverityService severityService;


    @GetMapping
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public Page<SeverityDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return severityService.getAll(pageable);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public SeverityDTO getById(
            @PathVariable Long id
    ) {

        return severityService.getById(id);
    }


    @PostMapping
    @PreAuthorize("hasAuthority('REFERENCE_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public SeverityDTO create(
            @RequestBody SeverityDTO severityDTO
    ) {

        return severityService.create(severityDTO);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public SearchResult<SeverityDTO> search(
            @RequestBody SeveritySearchQuery request) {
        return severityService.search(request);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_UPDATE')")
    public SeverityDTO update(
            @PathVariable Long id,
            @RequestBody SeverityDTO severityDTO
    ) {

        return severityService.update(id, severityDTO);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {

        severityService.delete(id);
    }
}