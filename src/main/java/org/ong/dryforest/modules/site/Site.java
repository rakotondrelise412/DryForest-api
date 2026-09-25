package org.ong.dryforest.modules.site;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.site.translation.SiteTranslation;
import org.ong.dryforest.modules.species.Species;
import org.ong.dryforest.shared.base.AbstractEntity;
import org.locationtech.jts.geom.Point;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sites")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Site extends AbstractEntity {

    @OneToMany(
            mappedBy = "site",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SiteTranslation> translations = new ArrayList<>();

    @Column(columnDefinition = "GEOMETRY(Point, 4326)")
    private Point location;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<Person> members = new ArrayList<>();

    @ManyToMany
    private List<Species> species = new ArrayList<>();
}