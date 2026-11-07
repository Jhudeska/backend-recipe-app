package nl.novi.recipeapp.dtos.userprofilerole;

import io.swagger.v3.oas.annotations.media.Schema;
import nl.novi.recipeapp.enums.Role;

import java.time.LocalDateTime;

@Schema(description = "Gegevens van een gebruikersrol als response")
public record UserProfileRoleResponseDto(

        @Schema(
                description = "Unieke identificatie van de gebruikersrol",
                example = "1"
        )
        Long userProfileRoleId,

        @Schema(
                description = "Rol die aan de gebruiker is toegewezen",
                example = "ADMIN"
        )
        Role role,

        @Schema(
                description = "Unieke identificatie van de gebruiker",
                example = "5"
        )
        Long userId,

        @Schema(
                description = "Datum en tijd waarop de gebruikersrol is aangemaakt",
                example = "2026-11-07T10:30:00"
        )
        LocalDateTime created_at,

        @Schema(
                description = "Datum en tijd waarop de gebruikersrol voor het laatst is gewijzigd",
                example = "2026-11-07T12:30:00"
        )
        LocalDateTime updated_at
) { }
