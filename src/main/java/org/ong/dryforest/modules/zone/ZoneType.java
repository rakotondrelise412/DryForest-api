package org.ong.dryforest.modules.zone;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.zone.translations.ZoneTypeTranslation;
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

    @OneToMany(mappedBy = "zoneType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ZoneTypeTranslation> translations = new ArrayList<>();
}
