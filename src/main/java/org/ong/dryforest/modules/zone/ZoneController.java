package org.ong.dryforest.modules.zone;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.dto.ZoneDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;

    @GetMapping
    public Page<ZoneDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        Sort.Direction.DESC,
                        "createdAt"
                )
        );

        return zoneService.getAll(pageable);
    }

    @GetMapping("/{uuid}")
    public ZoneDTO getByUuid(
            @PathVariable UUID uuid
    ) {

        return zoneService.getByUuid(uuid);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneDTO create(
            @RequestBody ZoneDTO dto
    ) {

        return zoneService.create(dto);
    }

    @PostMapping("/search")
    public SearchResult<ZoneDTO> search(
            @RequestBody ZoneSearchQuery request
    ) {

        return zoneService.search(request);
    }

    @PutMapping("/{uuid}")
    public ZoneDTO update(
            @PathVariable UUID uuid,
            @RequestBody ZoneDTO dto
    ) {

        return zoneService.update(uuid, dto);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID uuid
    ) {

        zoneService.delete(uuid);
    }
}