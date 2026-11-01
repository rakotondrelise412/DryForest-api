package org.ong.dryforest.modules.zone;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(
        name = "zone_translations",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"zone_id", "locale"}
                )
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ZoneTranslation extends AbstractTranslation {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;
}
