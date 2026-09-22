package org.ong.dryforest.modules.severity.translations;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.severity.Severity;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(name = "severity_translations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SeverityTranslation extends AbstractTranslation {

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Severity severity;
}