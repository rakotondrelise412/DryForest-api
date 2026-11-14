package org.ong.dryforest.modules.zone.zoneType;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.zoneType.dto.ZoneTypeDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/zone-types")
@RequiredArgsConstructor
public class ZoneTypeController {

    private final ZoneTypeService zoneTypeService;

    @GetMapping
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public Page<ZoneTypeDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "createdAt"));
        return zoneTypeService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public ZoneTypeDTO getById(
            @PathVariable Long id
    ) {
        return zoneTypeService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('REFERENCE_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneTypeDTO create(
            @RequestBody ZoneTypeDTO dto
    ) {
        return zoneTypeService.create(dto);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public SearchResult<ZoneTypeDTO> search(
            @RequestBody ZoneTypeSearchQuery request
    ) {
        return zoneTypeService.search(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_UPDATE')")
    public ZoneTypeDTO update(
            @PathVariable Long id,
            @RequestBody ZoneTypeDTO dto
    ) {
        return zoneTypeService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('REFERENCE_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        zoneTypeService.delete(id);
    }
}
