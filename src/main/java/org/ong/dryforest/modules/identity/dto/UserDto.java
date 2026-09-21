package org.ong.dryforest.modules.identity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record UserDto(
        @Nullable Long id,
        @NotNull @NotBlank String username,
        @Nullable String password,
        @NotNull @PositiveOrZero Long salary,
        @NotNull @NotBlank String phoneNumber,
        @NotNull @Email String email,
        @NotNull @NotBlank String address,
        @Nullable Long farmId
) {
}
