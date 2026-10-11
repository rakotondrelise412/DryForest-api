package org.ong.dryforest.modules.species.speciesZoneNeed;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SpeciesZoneNeedService {

    private final SpeciesZoneNeedRepository repository;
    private final SpeciesZoneNeedMapper mapper;

    @Transactional(readOnly = true)
    public List<SpeciesZoneNeedDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public SpeciesZoneNeedDTO findById(Long id) {
        SpeciesZoneNeed entity = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "SpeciesZoneNeed introuvable : " + id
                        )
                );

        return mapper.toDTO(entity);
    }

    @Transactional(readOnly = true)
    public SpeciesZoneNeedDTO findByUuid(UUID uuid) {
        SpeciesZoneNeed entity = repository.findByUuid(uuid)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "SpeciesZoneNeed introuvable : " + uuid
                        )
                );

        return mapper.toDTO(entity);
    }

    public SpeciesZoneNeedDTO create(SpeciesZoneNeedDTO dto) {
        SpeciesZoneNeed entity = mapper.toEntity(dto);

        // Une création doit générer un nouvel identifiant.
        entity.setId(null);

        if (entity.getUuid() == null) {
            entity.setUuid(UUID.randomUUID());
        }

        entity.setIsSynced(false);

        return mapper.toDTO(repository.save(entity));
    }

    public SpeciesZoneNeedDTO update(
            Long id,
            SpeciesZoneNeedDTO dto
    ) {
        SpeciesZoneNeed existing = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "SpeciesZoneNeed introuvable : " + id
                        )
                );

        dto.setId(id);

        SpeciesZoneNeed updated = mapper.toEntity(dto);
        updated.setId(existing.getId());

        return mapper.toDTO(repository.save(updated));
    }

    public void delete(Long id) {
        SpeciesZoneNeed entity = repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "SpeciesZoneNeed introuvable : " + id
                        )
                );

        repository.delete(entity);
    }

    @Transactional(readOnly = true)
    public List<SpeciesZoneNeedDTO> findBySpecies(Long idSpecies) {
        return repository.findAllBySpecies_Id(idSpecies)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SpeciesZoneNeedDTO> findByZoneNeed(Long idZoneNeed) {
        return repository.findAllByZoneNeed_Id(idZoneNeed)
                .stream()
                .map(mapper::toDTO)
                .toList();
    }
}

