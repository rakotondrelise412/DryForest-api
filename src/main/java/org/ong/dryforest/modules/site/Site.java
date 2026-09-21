package org.ong.dryforest.modules.site;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.locationtech.jts.geom.Point;
import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.species.Species;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sites")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Site extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(columnDefinition = "GEOMETRY(Point, 4326)")
    private @Nullable Point location;

    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<Person> members = new ArrayList<>();

    @ManyToMany
    private List<Species> species = new ArrayList<>();
}
