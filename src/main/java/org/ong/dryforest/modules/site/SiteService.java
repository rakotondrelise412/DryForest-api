package org.ong.dryforest.modules.site;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.site.dto.SiteDTO;
import org.ong.dryforest.modules.site.dto.SiteTranslationDTO;
import org.ong.dryforest.modules.site.translation.SiteTranslation;
import org.ong.dryforest.shared.exceptions.ErrorCode;
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



    @Transactional(readOnly = true)
    public Page<SiteDTO> getAll(Pageable pageable) {

        return siteRepository.findAll(pageable)
                .map(siteMapper::toDTO);
    }


    @Transactional(readOnly = true)
    public SiteDTO getById(Long id) {

        Site site = getSiteOrThrow(id);

        return siteMapper.toDTO(site);
    }


    public SiteDTO create(SiteDTO siteDTO) {

        Site site = siteMapper.toEntity(siteDTO);

        addTranslations(
                site,
                siteDTO.getTranslations()
        );

        Site savedSite =
                siteRepository.save(site);

        return siteMapper.toDTO(savedSite);
    }


    public SiteDTO update(
            Long id,
            SiteDTO siteDTO
    ) {

        Site site = getSiteOrThrow(id);

        site.setLocation(
                siteMapper.toEntity(siteDTO)
                        .getLocation()
        );

        updateTranslations(
                site,
                siteDTO.getTranslations()
        );

        Site updatedSite =
                siteRepository.saveAndFlush(site);


        return siteMapper.toDTO(updatedSite);
    }


    private void updateTranslations(
            Site site,
            List<SiteTranslationDTO> translationDTOs
    ) {

        for (SiteTranslationDTO dto : translationDTOs) {

            SiteTranslation translation =
                    site.getTranslations()
                            .stream()
                            .filter(t ->
                                    t.getLocale()
                                            .equals(dto.getLocale())
                            )
                            .findFirst()
                            .orElse(null);

            if (translation != null) {

                translation.setName(
                        dto.getName()
                );

            }
            else {

                SiteTranslation newTranslation =
                        siteMapper.toTranslation(
                                site,
                                dto
                        );

                site.getTranslations()
                        .add(newTranslation);
            }
        }
    }


    private void addTranslations(
            Site site,
            List<SiteTranslationDTO> translationDTOs
    ) {

        for (SiteTranslationDTO dto : translationDTOs) {

            SiteTranslation translation =
                    siteMapper.toTranslation(
                            site,
                            dto
                    );

            site.getTranslations()
                    .add(translation);
        }
    }


    public void delete(Long id) {

        Site site = getSiteOrThrow(id);

        siteRepository.delete(site);
    }


    private Site getSiteOrThrow(Long id) {

        return ServiceUtils.getOrThrow(
                () -> siteRepository.findById(id),
                ErrorCode.SITE_NOT_FOUND,
                "Site not found with id: " + id
        );
    }
}