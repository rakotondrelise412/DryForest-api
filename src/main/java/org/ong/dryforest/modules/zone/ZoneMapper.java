package org.ong.dryforest.modules.zone;

import org.locationtech.jts.geom.*;
import org.ong.dryforest.modules.zone.dto.*;
import org.ong.dryforest.modules.zone.translations.ZoneTranslation;
import org.ong.dryforest.modules.zone.translations.ZoneTranslationDTO;
import org.ong.dryforest.modules.zone.translations.ZoneTypeTranslation;
import org.ong.dryforest.modules.zone.translations.ZoneTypeTranslationDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ZoneMapper {

    private final GeometryFactory geometryFactory =
            new GeometryFactory();


    public ZoneDTO toDTO(Zone entity) {

        ZoneDTO dto = new ZoneDTO();

        dto.setId(entity.getId());
        dto.setUuid(entity.getUuid());
        dto.setArea(entity.getArea());
        dto.setIsSynced(entity.getIsSynced());

        if (entity.getTypeZone() != null) {
            dto.setTypeZoneId(
                    entity.getTypeZone().getId()
            );
        }

        // Site
        if (entity.getSite() != null) {
            dto.setSiteId(
                    entity.getSite().getId()
            );
        }

        if (entity.getGeom() != null) {
            dto.setGeom(
                    polygonToDTO(entity.getGeom())
            );
        }

        if (entity.getTranslations() != null) {
            dto.setTranslations(
                    entity.getTranslations()
                            .stream()
                            .map(this::toDTO)
                            .toList()
            );
        }

        return dto;
    }

    public Zone toEntity(ZoneDTO dto) {

        Zone entity = new Zone();

        entity.setUuid(dto.getUuid());
        entity.setArea(dto.getArea());
        entity.setIsSynced(dto.getIsSynced());

        if (dto.getGeom() != null) {
            entity.setGeom(
                    polygonToEntity(dto.getGeom())
            );
        }

        return entity;
    }


    public ZoneTranslationDTO toDTO(
            ZoneTranslation entity) {

        ZoneTranslationDTO dto =
                new ZoneTranslationDTO();

        dto.setId(entity.getId());
        dto.setLocale(entity.getLocale());
        dto.setName(entity.getName());

        return dto;
    }

    public ZoneTranslation toEntity(
            ZoneTranslationDTO dto) {

        ZoneTranslation entity =
                new ZoneTranslation();

        entity.setLocale(dto.getLocale());
        entity.setName(dto.getName());

        return entity;
    }


    public ZoneTypeDTO toDTO(ZoneType entity) {

        ZoneTypeDTO dto =
                new ZoneTypeDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());

        if (entity.getTranslations() != null) {

            dto.setTranslations(
                    entity.getTranslations()
                            .stream()
                            .map(this::toDTO)
                            .toList()
            );
        }

        return dto;
    }

    public ZoneType toEntity(ZoneTypeDTO dto) {

        ZoneType entity = new ZoneType();

        entity.setName(dto.getName());

        return entity;
    }


    public ZoneTypeTranslationDTO toDTO(
            ZoneTypeTranslation entity) {

        ZoneTypeTranslationDTO dto =
                new ZoneTypeTranslationDTO();

        dto.setId(entity.getId());
        dto.setLocale(entity.getLocale());
        dto.setName(entity.getName());

        return dto;
    }

    public ZoneTypeTranslation toEntity(
            ZoneTypeTranslationDTO dto) {

        ZoneTypeTranslation entity =
                new ZoneTypeTranslation();

        entity.setLocale(dto.getLocale());
        entity.setName(dto.getName());

        return entity;
    }


    public ZoneNeedDTO toDTO(ZoneNeed entity) {

        ZoneNeedDTO dto =
                new ZoneNeedDTO();

        dto.setId(entity.getId());
        dto.setUuid(entity.getUuid());
        dto.setIsSynced(entity.getIsSynced());

        if (entity.getZone() != null) {
            dto.setZoneId(
                    entity.getZone().getId()
            );
        }

        return dto;
    }

    public ZoneNeed toEntity(ZoneNeedDTO dto) {

        ZoneNeed entity =
                new ZoneNeed();

        entity.setUuid(dto.getUuid());
        entity.setIsSynced(dto.getIsSynced());

        return entity;
    }


    public PolygonDTO polygonToDTO(
            Polygon polygon) {

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


    public Polygon polygonToEntity(
            PolygonDTO dto) {

        if (dto == null ||
                dto.getCoordinates() == null ||
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

            List<Double> point =
                    points.get(i);

            coordinates[i] =
                    new Coordinate(
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