package org.ong.dryforest.modules.patrol;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.patrol.dto.PatrolGroupDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patrol-groups")
@RequiredArgsConstructor
public class PatrolGroupController {

    private final PatrolGroupService patrolGroupService;

    @GetMapping
    public Page<PatrolGroupDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return patrolGroupService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public PatrolGroupDTO getById(@PathVariable Long id) {

        return patrolGroupService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PatrolGroupDTO create(@RequestBody PatrolGroupDTO dto) {

        return patrolGroupService.create(dto);
    }

    @PostMapping("/search")
    public SearchResult<PatrolGroupDTO> search(
            @RequestBody PatrolGroupSearchQuery request
    ) {

        return patrolGroupService.search(request);
    }

    @PutMapping("/{id}")
    public PatrolGroupDTO update(
            @PathVariable Long id,
            @RequestBody PatrolGroupDTO dto
    ) {

        return patrolGroupService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        patrolGroupService.delete(id);
    }
}
