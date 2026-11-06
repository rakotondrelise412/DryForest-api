package org.ong.dryforest.modules.person;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.enums.Gender;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.shared.base.AbstractEntity;

@Entity
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person extends AbstractEntity {

    @Column(length = 200, nullable = false)
    private String lastName;

    @Column(length = 200, nullable = false)
    private String firstName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(length = 150, nullable = false)
    private String phoneNumber;

    @Nullable
    @Column(columnDefinition = "TEXT")
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id")
    private Site site;
}