package nl.novi.recipeapp.dtos.userprofilerole;

import nl.novi.recipeapp.enums.Role;

import java.time.LocalDateTime;

public record UserProfileRoleResponseDto(
        Long userProfileRoleId,
        Role role,
        Long userId,
        LocalDateTime created_at,
        LocalDateTime updated_at
) { }
