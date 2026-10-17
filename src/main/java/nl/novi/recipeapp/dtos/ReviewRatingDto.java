package nl.novi.recipeapp.dtos;

public record ReviewRatingDto(
        Long reviewRatingId,
        String comment,
        Integer ratingScore,
        Long recipeId,
        String recipeTitle,
        Long userId
) { }
