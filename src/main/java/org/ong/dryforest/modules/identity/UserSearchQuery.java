
package org.ong.dryforest.modules.identity;

import lombok.Getter;
import lombok.Setter;
import org.ong.dryforest.enums.Gender;
import org.ong.dryforest.enums.Role;
import org.ong.dryforest.shared.search.AbstractSearchQuery;
import org.ong.dryforest.shared.search.Searchable;

@Getter
@Setter
public class UserSearchQuery extends AbstractSearchQuery {

    @Searchable
    private Long id;

    @Searchable(type = Searchable.Type.CONTAINS)
    private String username;

    @Searchable
    private Long salary;

    @Searchable
    private Role role;

    @Searchable(field = "person.firstName",
            type = Searchable.Type.CONTAINS)
    private String firstName;

    @Searchable(field = "person.lastName",
            type = Searchable.Type.CONTAINS)
    private String lastName;

    @Searchable(field = "person.email",
            type = Searchable.Type.CONTAINS)
    private String email;

    @Searchable(field = "person.phoneNumber",
            type = Searchable.Type.CONTAINS)
    private String phoneNumber;

    @Searchable(field = "person.gender")
    private Gender gender;

    @Searchable(field = "person.site.id")
    private Long siteId;

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
