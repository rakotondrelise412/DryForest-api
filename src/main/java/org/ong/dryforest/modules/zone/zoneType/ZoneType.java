package org.ong.dryforest.modules.zone.zoneType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "zone_types")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ZoneType extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "zoneType",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ZoneTypeTranslation> translations = new ArrayList<>();
}