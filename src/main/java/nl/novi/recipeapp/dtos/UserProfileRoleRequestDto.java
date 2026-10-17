package nl.novi.recipeapp.dtos;

import jakarta.validation.constraints.NotNull;
import nl.novi.recipeapp.enums.Role;

public record UserProfileRoleRequestDto(

        @NotNull(message = "Role is required")
        Role role

) { }
