package org.ong.dryforest.modules.site;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.site.dto.SiteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sites")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    @GetMapping
    public ResponseEntity<Page<SiteDTO>> getAll(
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                siteService.getAll(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteDTO> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                siteService.getById(id)
        );
    }

    @PostMapping
    public ResponseEntity<SiteDTO> create(
            @RequestBody SiteDTO siteDTO
    ) {

        SiteDTO createdSite =
                siteService.create(siteDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdSite);
    }


    @PutMapping("/{id}")
    public ResponseEntity<SiteDTO> update(
            @PathVariable Long id,
            @RequestBody SiteDTO siteDTO
    ) {

        return ResponseEntity.ok(
                siteService.update(id, siteDTO)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        siteService.delete(id);

        return ResponseEntity.noContent().build();
    }
}