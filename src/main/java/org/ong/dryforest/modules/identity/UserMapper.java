package org.ong.dryforest.modules.identity;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.identity.dto.UserDto;
import org.ong.dryforest.modules.person.Person;
import org.ong.dryforest.modules.site.Site;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDto toDto(User user) {

        Person person = user.getPerson();

        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        // Ne jamais retourner le mot de passe.
        dto.setPassword(null);

        dto.setSalary(user.getSalary());
        dto.setRole(user.getRole());

        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setGender(person.getGender());
        dto.setPhoneNumber(person.getPhoneNumber());
        dto.setEmail(person.getEmail());
        dto.setAddress(person.getAddress());

        Site site = person.getSite();
        dto.setSiteId(site != null ? site.getId() : null);

        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}