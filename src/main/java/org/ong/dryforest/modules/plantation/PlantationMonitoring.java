package org.ong.dryforest.modules.plantation;

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

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "planting_monitoring")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlantationMonitoring extends AbstractEntity {
    public @Nullable Integer autoGeneration;
    private @Nullable LocalDate date;
    private Double diameter = 0.0d;
    private Double height = 0.0d;
    private Double density = 0.0d;
    private Double carbonSequestered = 0.0d;
    private @Nullable String image;
    private @Nullable Double isSynced;

    @Column(columnDefinition = "uuid")
    private @Nullable UUID uuid;

    @ManyToOne
    private @Nullable Plantation plantation;
}
