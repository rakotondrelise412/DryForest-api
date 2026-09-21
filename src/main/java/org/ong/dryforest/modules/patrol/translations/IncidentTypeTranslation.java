package org.ong.dryforest.modules.patrol.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.patrol.IncidentType;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "incident_type_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncidentTypeTranslation extends AbstractTranslation {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private IncidentType incidentType;
}
