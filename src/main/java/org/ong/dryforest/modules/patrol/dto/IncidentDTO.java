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
public class IncidentDTO {

    private Long id;

    private String image;

    private Boolean isSynced;

    private LocalDateTime incidentAt;

    private UUID uuid;

    private Double latitude;

    private Double longitude;

    private Long severityId;

    private Long incidentTypeId;

    private Long patrolGroupId;

    private Long userId;

    private Long zoneId;

    private Long plantationBlockId;

    private List<IncidentTranslationDTO> translations;
}
