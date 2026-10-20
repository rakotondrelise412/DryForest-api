package org.ong.dryforest.modules.species.speciesType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.species.speciesType.dto.SpeciesTypeDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/species-types")
@RequiredArgsConstructor
public class SpeciesTypeController {

    private final SpeciesTypeService speciesTypeService;

    @GetMapping
    public Page<SpeciesTypeDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        return speciesTypeService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public SpeciesTypeDTO getById(@PathVariable Long id) {
        return speciesTypeService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpeciesTypeDTO create(
            @RequestBody SpeciesTypeDTO dto
    ) {
        return speciesTypeService.create(dto);
    }

    @PostMapping("/search")
    public SearchResult<SpeciesTypeDTO> search(
            @RequestBody SpeciesTypeSearchQuery request
    ) {
        return speciesTypeService.search(request);
    }

    @PutMapping("/{id}")
    public SpeciesTypeDTO update(
            @PathVariable Long id,
            @RequestBody SpeciesTypeDTO dto
    ) {
        return speciesTypeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        speciesTypeService.delete(id);
    }
}

