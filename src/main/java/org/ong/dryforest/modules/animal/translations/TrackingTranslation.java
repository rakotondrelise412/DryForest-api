package org.ong.dryforest.modules.animal.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.animal.Tracking;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "tracking_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrackingTranslation extends AbstractTranslation {
    @Column(columnDefinition = "TEXT")
    private @Nullable String description;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Tracking tracking;
}
