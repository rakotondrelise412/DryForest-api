package org.ong.dryforest.modules.site;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.modules.site.dto.PointDTO;
import org.ong.dryforest.modules.site.dto.SiteDTO;
import org.ong.dryforest.modules.site.dto.SiteTranslationDTO;
import org.ong.dryforest.modules.site.translation.SiteTranslation;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SiteMapper {

    private final GeometryFactory geometryFactory =
            new GeometryFactory();


    public SiteDTO toDTO(Site site) {

        Long id = ServiceUtils.requireId(
                site.getId(),
                "Site ID must not be null"
        );

        var translations = site.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        PointDTO location = null;

        if (site.getLocation() != null) {

            Point point = site.getLocation();

            location = new PointDTO(
                    point.getY(),
                    point.getX()
            );
        }

        SiteDTO dto = new SiteDTO();

        dto.setId(id);
        dto.setTranslations(translations);
        dto.setLocation(location);

        return dto;
    }

    public Site toEntity(SiteDTO dto) {

        Site site = new Site();

        if (dto.getLocation() != null) {

            PointDTO location = dto.getLocation();

            Point point = geometryFactory.createPoint(
                    new Coordinate(
                            location.longitude(),
                            location.latitude()
                    )
            );

            point.setSRID(4326);

            site.setLocation(point);
        }

        return site;
    }


    public SiteTranslation toTranslation(
            Site site,
            SiteTranslationDTO dto
    ) {

        SiteTranslation translation =
                new SiteTranslation();

        translation.setSite(site);
        translation.setLocale(dto.getLocale());
        translation.setName(dto.getName());

        return translation;
    }


    private SiteTranslationDTO translationToDTO(
            SiteTranslation translation
    ) {

        Long id = ServiceUtils.requireId(
                translation.getId(),
                "SiteTranslation ID must not be null"
        );

        SiteTranslationDTO dto =
                new SiteTranslationDTO();

        dto.setId(id);
        dto.setLocale(translation.getLocale());
        dto.setName(translation.getName());

        return dto;
    }
}