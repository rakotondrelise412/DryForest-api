
package org.ong.dryforest.modules.species;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.species.dto.SpeciesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/species")
@RequiredArgsConstructor
public class SpeciesController {

    private final SpeciesService speciesService;

    @GetMapping
    public Page<SpeciesDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        return speciesService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public SpeciesDTO getById(@PathVariable Long id) {
        return speciesService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpeciesDTO create(@RequestBody SpeciesDTO dto) {
        return speciesService.create(dto);
    }

    @PostMapping("/search")
    public org.ong.dryforest.shared.search.SearchResult<SpeciesDTO> search(
            @RequestBody SpeciesSearchQuery request
    ) {
        return speciesService.search(request);
    }

    @PutMapping("/{id}")
    public SpeciesDTO update(
            @PathVariable Long id,
            @RequestBody SpeciesDTO dto
    ) {
        return speciesService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        speciesService.delete(id);
    }
}
