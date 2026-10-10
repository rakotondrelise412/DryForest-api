package org.ong.dryforest.modules.identity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.enums.Gender;
import org.ong.dryforest.shared.base.AbstractTimestamps;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@NullMarked
public class UserDto extends AbstractTimestamps {

        @Nullable
        private Long id;

        @NotNull
        @NotBlank
        private String username;

        @Nullable
        private String password;

        @NotNull
        @PositiveOrZero
        private Long salary;

        @NotNull
        @NotBlank
        private String firstName;

        @NotNull
        @NotBlank
        private String lastName;

        @NotNull
        private Gender gender;

        @NotNull
        @NotBlank
        private String phoneNumber;

        @NotNull
        @Email
        private String email;

        @Nullable
        private String address;
}