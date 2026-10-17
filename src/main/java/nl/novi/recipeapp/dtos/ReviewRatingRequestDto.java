package nl.novi.recipeapp.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewRatingRequestDto(

        @Size(max = 5000, message = "Comment cannot exceed 5000 characters")
        String comment,

        @NotNull(message = "Rating score is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot exceed 5")
        Integer ratingScore,

        @NotNull(message = "Recipe ID is required")
        Long recipeId

) { }
