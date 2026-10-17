package nl.novi.recipeapp.dtos.userprofile;

import java.time.LocalDateTime;

public record UserProfileResponseDto(
        Long userId,
        String username,
        String email,
        String profilePhotoUrl,
        String bio,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
