package org.ong.dryforest.modules.patrol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObservationDTO {

    private Long id;

    private LocalDateTime date;

    private Boolean isSynced;

    private UUID uuid;

    private Double latitude;

    private Double longitude;

    private Long observationTypeId;

    private Long patrolGroupId;

    private Long userId;

    private Long zoneId;

    private List<ObservationTranslationDTO> translations;
}
