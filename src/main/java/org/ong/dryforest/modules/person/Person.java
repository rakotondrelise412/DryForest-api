
package org.ong.dryforest.modules.person;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.enums.Gender;
import org.ong.dryforest.modules.site.Site;
import org.ong.dryforest.shared.base.AbstractEntity;
import org.ong.dryforest.shared.sort.SortableField;

@Entity
@Table(name = "persons")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NullMarked
public class Person extends AbstractEntity {

    @Nullable
    @SortableField(field = "createdAt", initialize = true)
    @Column(length = 200)
    private String lastName;

    @SortableField
    @Column(length = 200, nullable = false)
    private String firstName;

    @SortableField
    @Column(unique = true, nullable = false)
    private String email;

    @Nullable
    @Column(length = 150)
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

    public void setLastName(@Nullable String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }
}
