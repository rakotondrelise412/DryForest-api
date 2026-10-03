package org.ong.dryforest.modules.zone.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.dto.ZoneTypeDTO;
import org.ong.dryforest.modules.zone.service.ZoneTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/zone-types")
@RequiredArgsConstructor
public class ZoneTypeController {

    private final ZoneTypeService service;


    @PostMapping
    public ResponseEntity<ZoneTypeDTO> create(
            @RequestBody ZoneTypeDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }


    @GetMapping
    public ResponseEntity<Page<ZoneTypeDTO>> findAll(
            @PageableDefault(
                    page = 0,
                    size = 10
            )
            Pageable pageable) {

        return ResponseEntity.ok(
                service.findAll(pageable)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ZoneTypeDTO> findById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.findById(id)
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ZoneTypeDTO> update(
            @PathVariable Long id,
            @RequestBody ZoneTypeDTO dto) {

        return ResponseEntity.ok(
                service.update(id, dto)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}