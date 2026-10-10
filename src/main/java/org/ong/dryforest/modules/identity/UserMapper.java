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

        UserDto dto = new UserDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        dto.setPassword(null);

        dto.setSalary(user.getSalary());

        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setGender(person.getGender());
        String phoneNumber = person.getPhoneNumber();

        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalStateException(
                    "Le numéro de téléphone de la personne est absent."
            );
        }

        dto.setPhoneNumber(phoneNumber);

        dto.setEmail(person.getEmail());
        dto.setAddress(person.getAddress());

        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        return dto;
    }
}