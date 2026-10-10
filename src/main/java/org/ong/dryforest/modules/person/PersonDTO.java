package org.ong.dryforest.modules.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ong.dryforest.enums.Gender;
import org.ong.dryforest.shared.base.AbstractTimestamps;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO extends AbstractTimestamps {

    private Long id;

    private String lastName;

    private String firstName;

    private String email;

    private String phoneNumber;

    private String address;

    private Gender gender;

    private Long siteId;
}