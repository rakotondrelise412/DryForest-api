package org.ong.dryforest.modules.zone;

import jakarta.annotation.Nullable;
import org.locationtech.jts.geom.*;
import org.ong.dryforest.modules.zone.dto.*;
import org.ong.dryforest.modules.zone.dto.ZoneTranslationDTO;
import org.ong.dryforest.shared.utils.ServiceUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class ZoneMapper {

    private final GeometryFactory geometryFactory =
            new GeometryFactory();

    public ZoneDTO toDTO(Zone entity) {

        Objects.requireNonNull(entity, "Zone must not be null");

        var id = ServiceUtils.requireId(
                entity.getId(),
                "Zone ID must not be null"
        );

        ZoneDTO dto = new ZoneDTO();

        dto.setId(id);

        dto.setUuid(
                Objects.requireNonNull(
                        entity.getUuid(),
                        "Zone UUID must not be null"
                )
        );

        dto.setArea(entity.getArea());
        dto.setIsSynced(entity.getIsSynced());

        var typeZone = Objects.requireNonNull(
                entity.getTypeZone(),
                "Zone type must not be null"
        );

        dto.setTypeZoneId(
                ServiceUtils.requireId(
                        typeZone.getId(),
                        "TypeZone ID must not be null"
                )
        );

        var site = Objects.requireNonNull(
                entity.getSite(),
                "Zone site must not be null"
        );

        dto.setSiteId(
                ServiceUtils.requireId(
                        site.getId(),
                        "Site ID must not be null"
                )
        );

        if (entity.getGeom() != null) {
            dto.setGeom(polygonToDTO(entity.getGeom()));
        }

        dto.setTranslations(
                entity.getTranslations()
                        .stream()
                        .map(this::translationToDTO)
                        .toList()
        );

        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public Zone toEntity(ZoneDTO dto) {

        Zone zone = new Zone();

        zone.setUuid(dto.getUuid());
        zone.setArea(dto.getArea());
        zone.setIsSynced(dto.getIsSynced());

        zone.setGeom(
                polygonToEntity(dto.getGeom())
        );

        return zone;
    }


    public ZoneTranslationDTO translationToDTO(
            ZoneTranslation translation
    ) {

        var id = ServiceUtils.requireId(
                translation.getId(),
                "ZoneTranslation ID must not be null"
        );

        ZoneTranslationDTO dto = new ZoneTranslationDTO(
                id,
                translation.getLocale(),
                translation.getName()
        );

        dto.setCreatedAt(translation.getCreatedAt());
        dto.setUpdatedAt(translation.getUpdatedAt());

        return dto;
    }

    public ZoneTranslation toTranslation(
            Zone zone,
            ZoneTranslationDTO dto
    ) {

        ZoneTranslation translation =
                new ZoneTranslation();

        translation.setZone(zone);
        translation.setLocale(dto.getLocale());
        translation.setName(dto.getName().trim());

        return translation;
    }

    public PolygonDTO polygonToDTO(Polygon polygon) {

        List<List<Double>> points =
                new ArrayList<>();

        for (Coordinate coordinate :
                polygon.getCoordinates()) {

            points.add(
                    List.of(
                            coordinate.getX(),
                            coordinate.getY()
                    )
            );
        }

        List<List<List<Double>>> coordinates =
                new ArrayList<>();

        coordinates.add(points);

        return new PolygonDTO(
                "Polygon",
                coordinates
        );
    }

    @Nullable
    public Polygon polygonToEntity(
            @Nullable PolygonDTO dto
    ) {

        if (dto == null ||
                dto.getCoordinates().isEmpty()) {

            return null;
        }

        List<List<Double>> points =
                dto.getCoordinates().get(0);

        Coordinate[] coordinates =
                new Coordinate[points.size()];

        for (int i = 0;
             i < points.size();
             i++) {

            List<Double> point = points.get(i);

            coordinates[i] = new Coordinate(
                    point.get(0),
                    point.get(1)
            );
        }

        LinearRing ring =
                geometryFactory.createLinearRing(
                        coordinates
                );

        Polygon polygon =
                geometryFactory.createPolygon(ring);

        polygon.setSRID(4326);

        return polygon;
    }
}