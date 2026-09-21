package org.ong.dryforest.modules.patrol.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.patrol.Observation;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "observation_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObservationTranslation extends AbstractTranslation {
    @Column(columnDefinition = "TEXT")
    private @Nullable String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Observation observation;
}
