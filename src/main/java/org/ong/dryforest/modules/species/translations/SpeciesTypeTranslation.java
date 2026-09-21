package org.ong.dryforest.modules.species.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.species.SpeciesType;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "species_type_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpeciesTypeTranslation extends AbstractTranslation {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private SpeciesType speciesType;
}
