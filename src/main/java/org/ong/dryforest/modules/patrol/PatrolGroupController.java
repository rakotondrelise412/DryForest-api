
package org.ong.dryforest.modules.patrol;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.patrol.dto.PatrolGroupDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patrol-groups")
@RequiredArgsConstructor
public class PatrolGroupController {

    private final PatrolGroupService patrolGroupService;

    @GetMapping
    @PreAuthorize("hasAuthority('PATROL_READ')")
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
    @PreAuthorize("hasAuthority('PATROL_READ')")
    public PatrolGroupDTO getById(
            @PathVariable Long id
    ) {

        return patrolGroupService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PATROL_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public PatrolGroupDTO create(
            @RequestBody PatrolGroupDTO dto
    ) {

        return patrolGroupService.create(dto);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('PATROL_READ')")
    public SearchResult<PatrolGroupDTO> search(
            @RequestBody PatrolGroupSearchQuery request
    ) {

        return patrolGroupService.search(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PATROL_UPDATE')")
    public PatrolGroupDTO update(
            @PathVariable Long id,
            @RequestBody PatrolGroupDTO dto
    ) {

        return patrolGroupService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PATROL_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {

        patrolGroupService.delete(id);
    }
}
