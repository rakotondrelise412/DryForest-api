package org.ong.dryforest.modules.identity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.ong.dryforest.enums.Gender;

@NullMarked
public record UserDto(

        @Nullable
        Long id,

        @NotNull
        @NotBlank
        String username,

        @Nullable
        String password,

        @NotNull
        @PositiveOrZero
        Long salary,

        @NotNull
        @NotBlank
        String firstName,

        @NotNull
        @NotBlank
        String lastName,

        @NotNull
        Gender gender,

        @NotNull
        @NotBlank
        String phoneNumber,

        @NotNull
        @Email
        String email,

        @Nullable
        String address,

        @Nullable
        Long farmId

) {
}