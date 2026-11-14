
package org.ong.dryforest.modules.species.speciesType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.species.speciesType.dto.SpeciesTypeDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/species-types")
@RequiredArgsConstructor
public class SpeciesTypeController {

    private final SpeciesTypeService speciesTypeService;

    @GetMapping
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public Page<SpeciesTypeDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return speciesTypeService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public SpeciesTypeDTO getById(@PathVariable Long id) {
        return speciesTypeService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('REFERENCE_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public SpeciesTypeDTO create(
            @RequestBody SpeciesTypeDTO dto
    ) {
        return speciesTypeService.create(dto);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public SearchResult<SpeciesTypeDTO> search(
            @RequestBody SpeciesTypeSearchQuery request
    ) {
        return speciesTypeService.search(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_UPDATE')")
    public SpeciesTypeDTO update(
            @PathVariable Long id,
            @RequestBody SpeciesTypeDTO dto
    ) {
        return speciesTypeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        speciesTypeService.delete(id);
    }
}
