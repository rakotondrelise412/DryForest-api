package org.ong.dryforest.modules.species;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.modules.species.translations.SpeciesTranslation;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "species")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Species extends AbstractEntity {
    private String scientificName;
    private Double density = 0.0;

    @ManyToOne
    private @Nullable SpeciesType speciesType;

    @ManyToMany(mappedBy = "species")
    private List<Site> sites = new ArrayList<>();

    @OneToMany(mappedBy = "species", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SpeciesTranslation> translations = new ArrayList<>();
}
