package nl.novi.recipeapp.dtos.userprofile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Gegevens voor het bijwerken van een gebruikersprofiel")
public record UserProfileUpdateDto(

        @Schema(
                description = "Gebruikersnaam van de gebruiker",
                example = "JJansen",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Username is required")
        @Size(max = 100, message = "Username cannot exceed 100 characters")
        String username,

        @Schema(
                description = "E-mailadres van de gebruiker",
                example = "jan.jansen@receta.com",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "Email is required")
        @Email(message = "Please provide a valid email address")
        @Size(max = 255, message = "Email cannot exceed 255 characters")
        String email,

        @Schema(
                description = "URL van de profielfoto van de gebruiker",
                example = "https://receta.com/profile.jpg"
        )
        String profilePhotoUrl,

        @Schema(
                description = "Persoonlijke beschrijving van de gebruiker",
                example = "Ik hou van koken en het ontdekken van nieuwe recepten."
        )
        String bio

) { }
