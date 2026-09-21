package org.ong.dryforest.modules.severity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.base.AbstractEntity;

@Entity
@Table(name = "severities")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Severity extends AbstractEntity {
    @Column(nullable = false)
    private String name;
}
