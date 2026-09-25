package org.ong.dryforest.modules.site.translation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.shared.base.AbstractTranslation;

@Entity
@Table(
        name = "site_translations",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"site_id", "locale"}
                )
        }
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SiteTranslation extends AbstractTranslation {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;
}