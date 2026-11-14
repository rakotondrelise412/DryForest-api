
package org.ong.dryforest.modules.species;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.species.dto.SpeciesDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpeciesController {

    private final SpeciesService speciesService;

    @GetMapping
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public Page<SpeciesDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return speciesService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public SpeciesDTO getById(@PathVariable Long id) {
        return speciesService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('REFERENCE_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public SpeciesDTO create(@RequestBody SpeciesDTO dto) {
        return speciesService.create(dto);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public SearchResult<SpeciesDTO> search(
            @RequestBody SpeciesSearchQuery request
    ) {
        return speciesService.search(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_UPDATE')")
    public SpeciesDTO update(
            @PathVariable Long id,
            @RequestBody SpeciesDTO dto
    ) {
        return speciesService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        speciesService.delete(id);
    }
}
