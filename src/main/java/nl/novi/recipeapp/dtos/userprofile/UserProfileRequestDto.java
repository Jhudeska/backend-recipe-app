package nl.novi.recipeapp.dtos.userprofile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserProfileRequestDto(

        @NotBlank(message = "Username is required")
        @Size(max = 100, message = "Username cannot exceed 100 characters")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        @Size(max = 255, message = "Email cannot exceed 255 characters")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must contain at least 8 characters")
        String password,

        String profilePhotoUrl,

        String bio

) { }
