package nl.novi.recipeapp.dtos.userprofilerole;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import nl.novi.recipeapp.enums.Role;

@Schema(description = "Gegevens voor het aanmaken of wijzigen van een gebruikersrol")
public record UserProfileRoleRequestDto(

        @Schema(
                description = "Rol die aan de gebruiker wordt toegewezen",
                example = "ADMIN",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "Role is required")
        Role role,

        @Schema(
                description = "Unieke identificatie van de gebruiker waaraan de rol wordt toegewezen",
                example = "5",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotNull(message = "User ID is required")
        Long userId

) { }
