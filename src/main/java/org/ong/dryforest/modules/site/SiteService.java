package org.ong.dryforest.modules.site;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.site.dto.SiteDTO;
import org.ong.dryforest.modules.site.dto.SiteTranslationDTO;
import org.ong.dryforest.shared.exceptions.ErrorCode;
import org.ong.dryforest.shared.search.SearchResult;
import org.ong.dryforest.shared.search.SearchSpecification;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SiteService {

    private final SiteRepository siteRepository;
    private final SiteMapper siteMapper;
    private final SiteTranslationValidator translationValidator;

    @Transactional(readOnly = true)
    public Page<SiteDTO> getAll(Pageable pageable) {

        return siteRepository.findAll(pageable)
                .map(siteMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public SiteDTO getById(Long id) {

        return siteMapper.toDTO(getSiteOrThrow(id));
    }

    public SiteDTO create(SiteDTO siteDTO) {

        translationValidator.validateUniqueTranslationsForCreate(
                siteDTO.getTranslations()
        );

        Site site = siteMapper.toEntity(siteDTO);

        saveTranslations(
                site,
                siteDTO.getTranslations()
        );

        Site savedSite = siteRepository.save(site);

        return siteMapper.toDTO(savedSite);
    }

    public SiteDTO update(
            Long id,
            SiteDTO siteDTO
    ) {

        Site site = getSiteOrThrow(id);

        translationValidator.validateUniqueTranslationsForUpdate(
                site,
                siteDTO.getTranslations(),
                id
        );

        site.setLocation(
                siteMapper.toEntity(siteDTO).getLocation()
        );

        saveTranslations(
                site,
                siteDTO.getTranslations()
        );

        siteRepository.flush();

        return siteMapper.toDTO(site);
    }

    public void delete(Long id) {

        Site site = getSiteOrThrow(id);

        siteRepository.delete(site);
    }

    @Transactional(readOnly = true)
    public SearchResult<SiteDTO> search(
            SiteSearchQuery request
    ) {

        return SearchSpecification
                .filterBy(siteRepository, request)
                .map(siteMapper::toDTO);
    }

    private Site getSiteOrThrow(Long id) {

        return ServiceUtils.getOrThrow(
                () -> siteRepository.findById(id),
                ErrorCode.SITE_NOT_FOUND,
                "Site not found with id: " + id
        );
    }

    private void saveTranslations(
            Site site,
            List<SiteTranslationDTO> translationDTOs
    ) {

        for (SiteTranslationDTO dto : translationDTOs) {

            if (dto.getName().isBlank()) {
                continue;
            }

            SiteTranslation translation =
                    site.getTranslations().stream()
                            .filter(t -> t.getLocale() == dto.getLocale())
                            .findFirst()
                            .orElse(null);

            if (translation == null) {

                translation = siteMapper.toTranslation(
                        site,
                        dto
                );

                site.getTranslations().add(translation);

            } else {

                translation.setName(dto.getName().trim());
            }
        }
    }
}