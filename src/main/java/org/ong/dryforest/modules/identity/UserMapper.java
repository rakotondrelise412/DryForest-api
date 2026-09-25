package org.ong.dryforest.modules.identity;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.modules.identity.dto.UserDto;
import org.ong.dryforest.modules.person.Person;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserDto toDto(User user) {

        Person person = user.getPerson();

        return new UserDto(
                user.getId(),
                user.getUsername(),
                null,
                user.getSalary(),

                person.getFirstName(),
                person.getLastName(),
                person.getGender(),

                person.getPhoneNumber(),
                person.getEmail(),
                person.getAddress(),

                null
        );
    }
}