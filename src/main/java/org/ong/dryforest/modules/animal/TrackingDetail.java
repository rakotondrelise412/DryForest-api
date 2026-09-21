package org.ong.dryforest.modules.animal;

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
@Table(name = "tracking_details")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TrackingDetail extends AbstractEntity {
    private Boolean haveSeen = false;
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @ManyToOne
    private @Nullable Animal animal;

    @ManyToOne
    private @Nullable Tracking tracking;
}
