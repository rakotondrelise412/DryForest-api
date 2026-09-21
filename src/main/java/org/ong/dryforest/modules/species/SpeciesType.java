package org.ong.dryforest.modules.species;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.species.translations.SpeciesTypeTranslation;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "species_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SpeciesType extends AbstractEntity {
    @OneToMany(mappedBy = "speciesType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpeciesTypeTranslation> translations = new ArrayList<>();
}
