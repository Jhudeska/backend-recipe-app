package nl.novi.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.userprofilerole.UserProfileRoleRequestDto;
import nl.novi.recipeapp.dtos.userprofilerole.UserProfileRoleResponseDto;
import nl.novi.recipeapp.services.UserProfileRoleService;
import nl.novi.recipeapp.utils.LocationUriBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user_profile_roles")
@PreAuthorize("hasRole('ADMIN')")
@Tag(
        name = "Gebruikersrollen",
        description = "API voor het beheren van gebruikersrollen"
)
public class UserProfileRoleController {

    private final UserProfileRoleService userProfileRoleService;
    private final LocationUriBuilder locationUriBuilder;

    public UserProfileRoleController(
            UserProfileRoleService userRoleService, LocationUriBuilder locationUriBuilder) {
        this.userProfileRoleService = userRoleService;
        this.locationUriBuilder = locationUriBuilder;
    }

    @GetMapping
    @Operation(
            summary = "Alle gebruikersrollen ophalen",
            description = "Haalt alle gebruikersrollen op. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<List<UserProfileRoleResponseDto>> getAllUserRoles() {
        return ResponseEntity.ok(
                userProfileRoleService.getAllUserRoles()
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Gebruikersrol ophalen",
            description = "Haalt een specifieke gebruikersrol op aan de hand van het ID. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<UserProfileRoleResponseDto> getUserRoleById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                userProfileRoleService.getUserRoleById(id)
        );
    }

    @PostMapping
    @Operation(
            summary = "Gebruikersrol aanmaken",
            description = "Koppelt een rol aan een gebruikersprofiel. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<UserProfileRoleResponseDto> createUserRole(
            @Valid @RequestBody UserProfileRoleRequestDto requestDto ) {

        UserProfileRoleResponseDto createdUserRole =
                userProfileRoleService.createUserRole(requestDto);

        URI location = locationUriBuilder.buildLocation(
                createdUserRole.userProfileRoleId()
        );

        return ResponseEntity
                .created(location)
                .body(createdUserRole);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Gebruikersrol wijzigen",
            description = "Wijzigt een bestaande gebruikersrol op basis van het ID. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<UserProfileRoleResponseDto> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UserProfileRoleRequestDto requestDto) {

        return ResponseEntity.ok(
                userProfileRoleService.updateUserRole(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Gebruikersrol verwijderen",
            description = "Verwijdert een gebruikersrol op basis van het ID. Alleen ADMIN heeft toegang."
    )
    public ResponseEntity<Void> deleteUserRole(
            @PathVariable Long id) {

        userProfileRoleService.deleteUserRole(id);

        return ResponseEntity.noContent().build();
    }
}
