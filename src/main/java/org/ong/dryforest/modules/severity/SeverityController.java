package org.ong.dryforest.modules.severity;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.severity.dto.SeverityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/severities")
@RequiredArgsConstructor
public class SeverityController {

    private final SeverityService severityService;


    @GetMapping
    public Page<SeverityDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return severityService.getAll(pageable);
    }


    @GetMapping("/{id}")
    public SeverityDTO getById(
            @PathVariable Long id
    ) {

        return severityService.getById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeverityDTO create(
            @RequestBody SeverityDTO severityDTO
    ) {

        return severityService.create(severityDTO);
    }


    @PutMapping("/{id}")
    public SeverityDTO update(
            @PathVariable Long id,
            @RequestBody SeverityDTO severityDTO
    ) {

        return severityService.update(id, severityDTO);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {

        severityService.delete(id);
    }
}