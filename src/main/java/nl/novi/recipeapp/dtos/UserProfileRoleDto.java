package nl.novi.recipeapp.dtos;

import nl.novi.recipeapp.enums.Role;

public record UserProfileRoleDto(
        Long userProfileRoleId,
        Role role,
        Long userId
) { }
