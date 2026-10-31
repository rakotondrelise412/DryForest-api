
package org.ong.dryforest.modules.site;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.modules.site.dto.PointDTO;
import org.ong.dryforest.modules.site.dto.SiteDTO;
import org.ong.dryforest.modules.site.dto.SiteTranslationDTO;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

@Component
public class SiteMapper {

    private final GeometryFactory geometryFactory =
            new GeometryFactory();

    public SiteDTO toDTO(Site site) {

        var id = ServiceUtils.requireId(
                site.getId(),
                "Site ID must not be null"
        );

        var translations = site.getTranslations()
                .stream()
                .map(this::translationToDTO)
                .toList();

        Point point = site.getLocation();

        PointDTO location = new PointDTO(
                point.getY(),
                point.getX()
        );

        SiteDTO dto = new SiteDTO();

        dto.setId(id);
        dto.setTranslations(translations);
        dto.setLocation(location);
        dto.setCreatedAt(site.getCreatedAt());
        dto.setUpdatedAt(site.getUpdatedAt());

        return dto;
    }

    public Site toEntity(SiteDTO dto) {

        Site site = new Site();

        PointDTO location = dto.getLocation();

        Point point = geometryFactory.createPoint(
                new Coordinate(
                        location.longitude(),
                        location.latitude()
                )
        );

        point.setSRID(4326);

        site.setLocation(point);

        return site;
    }

    public SiteTranslation toTranslation(
            Site site,
            SiteTranslationDTO dto
    ) {

        SiteTranslation translation = new SiteTranslation();

        translation.setSite(site);
        translation.setLocale(dto.getLocale());
        translation.setName(dto.getName().trim());

        return translation;
    }

    private SiteTranslationDTO translationToDTO(
            SiteTranslation translation
    ) {

        var id = ServiceUtils.requireId(
                translation.getId(),
                "SiteTranslation ID must not be null"
        );

        var dto = new SiteTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );

        dto.setCreatedAt(translation.getCreatedAt());
        dto.setUpdatedAt(translation.getUpdatedAt());

        return dto;
    }
}
