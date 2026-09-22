package org.ong.dryforest.modules.severity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.severity.translations.SeverityTranslation;
import org.ong.dryforest.shared.base.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "severities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Severity extends AbstractEntity {

    @OneToMany(
            mappedBy = "severity",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SeverityTranslation> translations =
            new ArrayList<>();
}