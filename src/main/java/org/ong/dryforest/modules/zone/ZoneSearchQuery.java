package org.ong.dryforest.modules.zone;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.search.AbstractSearchQuery;
import org.ong.dryforest.shared.search.Searchable;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneSearchQuery extends AbstractSearchQuery {

    @Searchable
    private Long id;

    @Searchable
    private UUID uuid;

    @Searchable
    private Double area;

    @Searchable(
            field = "translations.name",
            type = Searchable.Type.CONTAINS
    )
    private String name;

    @Override
    protected Class<?> getEntityClass() {

        return Zone.class;
    }
}
