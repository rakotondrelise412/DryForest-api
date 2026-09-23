package org.ong.dryforest.modules.severity;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.severity.dto.SeverityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/severities")
@RequiredArgsConstructor
public class SeverityController {

    private final SeverityService severityService;

    @GetMapping
    public ResponseEntity<Page<SeverityDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                severityService.getAll(pageable)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<SeverityDTO> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                severityService.getById(id)
        );
    }


    @PostMapping
    public ResponseEntity<SeverityDTO> create(
            @RequestBody SeverityDTO severityDTO
    ) {

        return ResponseEntity.ok(
                severityService.create(severityDTO)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeverityDTO> update(
            @PathVariable Long id,
            @RequestBody SeverityDTO severityDTO
    ) {

        return ResponseEntity.ok(
                severityService.update(id, severityDTO)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        severityService.delete(id);

        return ResponseEntity.noContent().build();
    }
}