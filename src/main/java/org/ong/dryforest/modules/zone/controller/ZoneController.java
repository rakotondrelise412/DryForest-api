package org.ong.dryforest.modules.zone.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.dto.ZoneDTO;
import org.ong.dryforest.modules.zone.service.ZoneService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/zones")
@RequiredArgsConstructor
public class ZoneController {

    private final ZoneService service;


    @PostMapping
    public ResponseEntity<ZoneDTO> create(
            @RequestBody ZoneDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }


    @GetMapping
    public ResponseEntity<Page<ZoneDTO>> findAll(
            @PageableDefault(
                    page = 0,
                    size = 10
            )
            Pageable pageable) {

        return ResponseEntity.ok(
                service.findAll(pageable)
        );
    }


    @GetMapping("/{uuid}")
    public ResponseEntity<ZoneDTO> findByUuid(
            @PathVariable UUID uuid) {

        return ResponseEntity.ok(
                service.findByUuid(uuid)
        );
    }


    @PutMapping("/{uuid}")
    public ResponseEntity<ZoneDTO> update(
            @PathVariable UUID uuid,
            @RequestBody ZoneDTO dto) {

        return ResponseEntity.ok(
                service.update(uuid, dto)
        );
    }


    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID uuid) {

        service.delete(uuid);

        return ResponseEntity.noContent().build();
    }
}