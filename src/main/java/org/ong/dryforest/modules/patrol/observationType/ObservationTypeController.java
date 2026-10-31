package org.ong.dryforest.modules.patrol.observationType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.patrol.observationType.dto.ObservationTypeDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/observation-types")
@RequiredArgsConstructor
public class ObservationTypeController {

    private final ObservationTypeService observationTypeService;

    @GetMapping
    public Page<ObservationTypeDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return observationTypeService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ObservationTypeDTO getById(@PathVariable Long id) {

        return observationTypeService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ObservationTypeDTO create(
            @RequestBody ObservationTypeDTO dto
    ) {
        return observationTypeService.create(dto);
    }

    @PostMapping("/search")
    public SearchResult<ObservationTypeDTO> search(
            @RequestBody ObservationTypeSearchQuery request
    ) {
        return observationTypeService.search(request);
    }

    @PutMapping("/{id}")
    public ObservationTypeDTO update(
            @PathVariable Long id,
            @RequestBody ObservationTypeDTO dto
    ) {
        return observationTypeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        observationTypeService.delete(id);
    }
}
