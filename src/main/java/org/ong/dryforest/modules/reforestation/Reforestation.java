package org.ong.dryforest.modules.reforestation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.modules.zone.Zone;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reforestation")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reforestation extends AbstractEntity {
    private @Nullable LocalDate date;
    private Integer quantity = 0;
    private Boolean isSynced = false;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @ManyToOne
    private @Nullable Zone zone;

    @OneToMany(mappedBy = "reforestation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReforestationDetail> reforestationDetail = new ArrayList<>();
}
