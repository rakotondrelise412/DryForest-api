
package org.ong.dryforest.modules.zone.zoneNeed;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.shared.search.AbstractSearchQuery;
import org.ong.dryforest.shared.search.Searchable;

@Getter
@Setter
@NoArgsConstructor
public class ZoneNeedSearchQuery extends AbstractSearchQuery {

    @Searchable
    private Long id;

    @Searchable
    private String uuid;

    @Searchable
    private Boolean isSynced;

    @Searchable(field = "zone.id")
    private Long zoneId;

    @Override
    protected Class<?> getEntityClass() {
        return ZoneNeed.class;
    }
}
