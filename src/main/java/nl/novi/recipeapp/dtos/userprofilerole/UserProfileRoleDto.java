package nl.novi.recipeapp.dtos.userprofilerole;

import nl.novi.recipeapp.enums.Role;

public record UserProfileRoleDto(
        Long userProfileRoleId,
        Role role,
        Long userId
) { }
