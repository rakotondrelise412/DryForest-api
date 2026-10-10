package org.ong.dryforest.modules.zone.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.dto.ZoneTypeDTO;
import org.ong.dryforest.modules.zone.service.ZoneTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zone-types")
@RequiredArgsConstructor
public class ZoneTypeController {

    private final ZoneTypeService zoneTypeService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ZoneTypeDTO create(
            @RequestBody ZoneTypeDTO dto) {

        return zoneTypeService.create(dto);
    }

    @GetMapping
    public Page<ZoneTypeDTO> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page, size);
        return zoneTypeService.findAll(pageable);
    }


    @GetMapping("/{id}")
    public ZoneTypeDTO findById(
            @PathVariable Long id) {

        return zoneTypeService.findById(id);
    }


    @PutMapping("/{id}")
    public ZoneTypeDTO update(
            @PathVariable Long id,
            @RequestBody ZoneTypeDTO dto) {

        return zoneTypeService.update(id, dto);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {

        zoneTypeService.delete(id);
    }
}