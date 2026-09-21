package org.ong.dryforest.modules.zone.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.zone.Zone;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "zone_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ZoneTranslation extends AbstractTranslation {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Zone zone;
}
