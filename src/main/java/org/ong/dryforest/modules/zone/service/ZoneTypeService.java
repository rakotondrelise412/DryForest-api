package org.ong.dryforest.modules.zone.service;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.zone.ZoneMapper;
import org.ong.dryforest.modules.zone.ZoneType;
import org.ong.dryforest.modules.zone.dto.ZoneTypeDTO;
import org.ong.dryforest.modules.zone.repository.ZoneTypeRepository;
import org.ong.dryforest.modules.zone.translations.ZoneTypeTranslation;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ZoneTypeService {

    private final ZoneTypeRepository repository;

    private final ZoneMapper mapper;


    @Transactional
    public ZoneTypeDTO create(ZoneTypeDTO dto) {

        ZoneType entity =
                mapper.toEntity(dto);

        if (dto.getTranslations() != null) {

            dto.getTranslations()
                    .forEach(translationDTO -> {

                        ZoneTypeTranslation translation =
                                mapper.toEntity(
                                        translationDTO
                                );

                        translation.setZoneType(entity);

                        entity.getTranslations()
                                .add(translation);
                    });
        }

        return mapper.toDTO(
                repository.save(entity)
        );
    }


    @Transactional(readOnly = true)
    public Page<ZoneTypeDTO> findAll(
            Pageable pageable) {

        return repository
                .findAllByIsDeletedFalse(pageable)
                .map(mapper::toDTO);
    }


    @Transactional(readOnly = true)
    public ZoneTypeDTO findById(Long id) {

        ZoneType entity =
                ServiceUtils.getOrThrow(
                        () -> repository.findById(id),
                        ErrorCode.ZONE_TYPE_NOT_FOUND,
                        "Type de zone introuvable"
                );

        return mapper.toDTO(entity);
    }


    @Transactional
    public ZoneTypeDTO update(
            Long id,
            ZoneTypeDTO dto) {

        ZoneType entity =
                ServiceUtils.getOrThrow(
                        () -> repository.findById(id),
                        ErrorCode.ZONE_TYPE_NOT_FOUND,
                        "Type de zone introuvable"
                );

        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }

        if (dto.getTranslations() != null) {

            entity.getTranslations().clear();

            dto.getTranslations()
                    .forEach(translationDTO -> {

                        ZoneTypeTranslation translation =
                                mapper.toEntity(
                                        translationDTO
                                );

                        translation.setZoneType(entity);

                        entity.getTranslations()
                                .add(translation);
                    });
        }

        return mapper.toDTO(
                repository.save(entity)
        );
    }


    @Transactional
    public void delete(Long id) {

        ZoneType entity =
                ServiceUtils.getOrThrow(
                        () -> repository.findById(id),
                        ErrorCode.ZONE_TYPE_NOT_FOUND,
                        "Type de zone introuvable"
                );

        entity.setDeleted(true);

        repository.save(entity);
    }
}