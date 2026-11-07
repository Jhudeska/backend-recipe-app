package nl.novi.recipeapp.dtos.userprofile;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Gegevens van een gebruikersprofiel als response")
public record UserProfileResponseDto(
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
        String bio,

        @Schema(
                description = "Datum en tijd waarop het gebruikersprofiel is aangemaakt",
                example = "2026-11-07T10:30:00"
        )
        LocalDateTime created_at,

        @Schema(
                description = "Datum en tijd waarop het gebruikersprofiel voor het laatst is gewijzigd",
                example = "2026-11-07T12:30:00"
        )
        LocalDateTime updated_at
) {
}
