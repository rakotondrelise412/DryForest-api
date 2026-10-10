package org.ong.dryforest.modules.site;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.site.dto.SiteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sites")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    @GetMapping
    public Page<SiteDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return siteService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public SiteDTO getById(
            @PathVariable Long id
    ) {

        return siteService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SiteDTO create(
            @RequestBody SiteDTO siteDTO) {

        return siteService.create(siteDTO);
    }


    @PutMapping("/{id}")
    public SiteDTO update(
            @PathVariable Long id,
            @RequestBody SiteDTO siteDTO
    ) {

        return siteService.update(id, siteDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {

        siteService.delete(id);
    }
}