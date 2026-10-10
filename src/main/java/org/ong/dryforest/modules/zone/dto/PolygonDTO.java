package org.ong.dryforest.modules.zone.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PolygonDTO {

    private String type;

    private List<List<List<Double>>> coordinates;
}
