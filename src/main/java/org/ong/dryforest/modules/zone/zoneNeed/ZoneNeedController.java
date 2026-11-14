
package org.ong.dryforest.modules.zone.zoneNeed;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/zone-needs")
@RequiredArgsConstructor
public class ZoneNeedController {

    private final ZoneNeedService zoneNeedService;

    @PostMapping
    @PreAuthorize("hasAuthority('REFERENCE_CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneNeedDTO create(@RequestBody ZoneNeedDTO dto) {
        return zoneNeedService.create(dto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public Page<ZoneNeedDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return zoneNeedService.findAll(page, size);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public SearchResult<ZoneNeedDTO> search(
            @RequestBody ZoneNeedSearchQuery query) {
        return zoneNeedService.search(query);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasAuthority('REFERENCE_READ')")
    public ZoneNeedDTO findByUuid(@PathVariable UUID uuid) {
        return zoneNeedService.findByUuid(uuid);
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAuthority('REFERENCE_UPDATE')")
    public ZoneNeedDTO update(
            @PathVariable UUID uuid,
            @RequestBody ZoneNeedDTO dto) {
        return zoneNeedService.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAuthority('REFERENCE_DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID uuid) {
        zoneNeedService.delete(uuid);
    }
}
