package org.ong.dryforest.modules.zone;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.dto.ZoneDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService zoneService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneDTO create(
            @RequestBody ZoneDTO dto) {

        return zoneService.create(dto);
    }


    @GetMapping
    public Page<ZoneDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page, size);

        return zoneService.findAll(pageable);
    }

    @GetMapping("/{uuid}")
    public ZoneDTO findByUuid(
            @PathVariable UUID uuid) {

        return zoneService.findByUuid(uuid);
    }

    @PostMapping("/search")
    public SearchResult<Zone> search(
            @RequestBody ZoneSearchQuery request) {

        return zoneService.search(request);
    }

    @PutMapping("/{uuid}")
    public ZoneDTO update(
            @PathVariable UUID uuid,
            @RequestBody ZoneDTO dto) {

        return zoneService.update(uuid, dto);
    }


    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID uuid) {

        zoneService.delete(uuid);
    }
}