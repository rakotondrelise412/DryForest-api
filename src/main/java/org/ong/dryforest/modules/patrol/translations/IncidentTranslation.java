package org.ong.dryforest.modules.patrol.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.patrol.Incident;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "incident_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class IncidentTranslation extends AbstractTranslation {
    @Column(columnDefinition = "TEXT")
    private @Nullable String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Incident incident;
}
