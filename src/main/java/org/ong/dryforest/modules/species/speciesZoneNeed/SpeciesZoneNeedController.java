package org.ong.dryforest.modules.species.speciesZoneNeed;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/species-zone-needs")
@RequiredArgsConstructor
public class SpeciesZoneNeedController {

    private final SpeciesZoneNeedService service;

    @GetMapping
    public List<SpeciesZoneNeedDTO> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SpeciesZoneNeedDTO findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping("/uuid/{uuid}")
    public SpeciesZoneNeedDTO findByUuid(
            @PathVariable UUID uuid
    ) {
        return service.findByUuid(uuid);
    }

    @PostMapping
    public SpeciesZoneNeedDTO create(
            @RequestBody SpeciesZoneNeedDTO dto
    ) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public SpeciesZoneNeedDTO update(
            @PathVariable Long id,
            @RequestBody SpeciesZoneNeedDTO dto
    ) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping("/species/{idSpecies}")
    public List<SpeciesZoneNeedDTO> findBySpecies(
            @PathVariable Long idSpecies
    ) {
        return service.findBySpecies(idSpecies);
    }

    @GetMapping("/zone-need/{idZoneNeed}")
    public List<SpeciesZoneNeedDTO> findByZoneNeed(
            @PathVariable Long idZoneNeed
    ) {
        return service.findByZoneNeed(idZoneNeed);
    }
}

