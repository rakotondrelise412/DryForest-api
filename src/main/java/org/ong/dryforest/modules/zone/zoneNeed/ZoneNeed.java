
package org.ong.dryforest.modules.zone.zoneNeed;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.zone.Zone;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.UUID;

@Entity
@Table(name = "zone_needs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ZoneNeed extends AbstractEntity {

    @Column(name = "is_synced", nullable = false)
    private Boolean isSynced = false;

    @Column(name = "uuid", columnDefinition = "uuid", unique = true)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_id")
    private Zone zone;
}
