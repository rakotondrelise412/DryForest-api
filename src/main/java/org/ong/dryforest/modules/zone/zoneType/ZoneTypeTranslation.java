package org.ong.dryforest.modules.zone.zoneType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "zone_type_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ZoneTypeTranslation extends AbstractTranslation {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private ZoneType zoneType;
}