package nl.novi.recipeapp.dtos;

public record UserProfileDto(
        Long userId,
        String username,
        String email,
        String profilePhotoUrl,
        String bio
) { }
