package org.ong.dryforest.modules.patrol.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.patrol.ObservationType;
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

    @ManyToOne
    @JoinColumn(nullable = false)
    private ObservationType observationType;
}
