package org.ong.dryforest.modules.zone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.UUID;

@Entity
@Table(name = "zone_needs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ZoneNeed extends AbstractEntity {
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @ManyToOne
    private @Nullable  Zone zone;
}
