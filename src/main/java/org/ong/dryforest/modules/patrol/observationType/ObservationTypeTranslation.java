package org.ong.dryforest.modules.patrol.observationType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "observation_type_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ObservationTypeTranslation extends AbstractTranslation {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "observation_type_id", nullable = false)
    private ObservationType observationType;
}
