package org.ong.dryforest.modules.zone.zoneType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.search.AbstractSearchQuery;
import org.ong.dryforest.shared.search.Searchable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZoneTypeSearchQuery extends AbstractSearchQuery {

    @Searchable
    private Long id;

    @Searchable(
            field = "name",
            type = Searchable.Type.CONTAINS,
            isEltOr = true
    )
    private String name;

    @Searchable(
            field = "translations.name",
            type = Searchable.Type.CONTAINS,
            isEltOr = true
    )
    private String translationName;

    @Override
    protected Class<?> getEntityClass() {
        return ZoneType.class;
    }
}