package org.ong.dryforest.modules.person.dto;


import lombok.Getter;
import lombok.Setter;
import org.ong.dryforest.enums.Gender;

@Getter
@Setter
public class PersonDTO {

    private String lastName;

    private String firstName;

    private String email;

    private String phoneNumber;

    private String address;

    private Gender gender;

    private Long siteId;
}
