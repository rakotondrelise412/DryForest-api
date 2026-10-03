package org.ong.dryforest.modules.zone.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.dto.ZoneNeedDTO;
import org.ong.dryforest.modules.zone.service.ZoneNeedService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/zone-needs")
@RequiredArgsConstructor
public class ZoneNeedController {

    private final ZoneNeedService service;


    @PostMapping
    public ResponseEntity<ZoneNeedDTO> create(
            @RequestBody ZoneNeedDTO dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }


    @GetMapping
    public ResponseEntity<Page<ZoneNeedDTO>> findAll(
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
    public ResponseEntity<ZoneNeedDTO> findByUuid(
            @PathVariable UUID uuid) {

        return ResponseEntity.ok(
                service.findByUuid(uuid)
        );
    }


    @PutMapping("/{uuid}")
    public ResponseEntity<ZoneNeedDTO> update(
            @PathVariable UUID uuid,
            @RequestBody ZoneNeedDTO dto) {

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