
package org.ong.dryforest.modules.identity;

import org.ong.dryforest.modules.identity.dto.UserDto;
import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.site.Site;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserMapper {

    public UserDto toDto(User user) {

        Person person = user.getPerson();

        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(null);

        dto.setSalary(user.getSalary());
        dto.setRole(user.getRole());

        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setGender(person.getGender());

        dto.setPhoneNumber(
                Objects.requireNonNull(
                        person.getPhoneNumber(),
                        "Un utilisateur doit obligatoirement avoir un numéro de téléphone."
                )
        );

        dto.setEmail(person.getEmail());
        dto.setAddress(person.getAddress());

        Site site = person.getSite();

        dto.setSiteId(site != null ? site.getId() : null);

        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}
