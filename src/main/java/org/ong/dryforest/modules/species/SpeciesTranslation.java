
package org.ong.dryforest.modules.species;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "species_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpeciesTranslation extends AbstractTranslation {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Species species;
}
