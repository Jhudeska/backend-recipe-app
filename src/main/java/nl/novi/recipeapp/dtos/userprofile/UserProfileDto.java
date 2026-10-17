package nl.novi.recipeapp.dtos.userprofile;

public record UserProfileDto(
        Long userId,
        String username,
        String email,
        String profilePhotoUrl,
        String bio
) { }
