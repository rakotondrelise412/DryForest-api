
package org.ong.dryforest.modules.site;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.site.dto.SiteDTO;
import org.ong.dryforest.shared.search.SearchResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sites")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    @GetMapping
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).SITE_READ)")
    public Page<SiteDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return siteService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).SITE_READ)")
    public SiteDTO getById(
            @PathVariable Long id
    ) {
        return siteService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).SITE_CREATE)")
    public SiteDTO create(
            @RequestBody SiteDTO siteDTO
    ) {
        return siteService.create(siteDTO);
    }

    @PostMapping("/search")
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).SITE_READ)")
    public SearchResult<SiteDTO> search(
            @RequestBody SiteSearchQuery request
    ) {
        return siteService.search(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).SITE_UPDATE)")
    public SiteDTO update(
            @PathVariable Long id,
            @RequestBody SiteDTO siteDTO
    ) {
        return siteService.update(id, siteDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("@permissionService.hasPermission(T(org.ong.dryforest.enums.Permission).SITE_DELETE)")
    public void delete(
            @PathVariable Long id
    ) {
        siteService.delete(id);
    }
}
