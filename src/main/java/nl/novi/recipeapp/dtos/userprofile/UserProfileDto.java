package nl.novi.recipeapp.dtos.userprofile;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserProfileDto(

        @Schema(
                description = "Unieke identificatie van de gebruiker",
                example = "1"
        )
        Long userId,

        @Schema(
                description = "Gebruikersnaam van de gebruiker",
                example = "JJansen"
        )
        String username,

        @Schema(
                description = "E-mailadres van de gebruiker",
                example = "jan.jansen@receta.com"
        )
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
