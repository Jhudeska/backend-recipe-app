package nl.novi.recipeapp.controllers;

import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.userprofilerole.UserProfileRoleRequestDto;
import nl.novi.recipeapp.dtos.userprofilerole.UserProfileRoleResponseDto;
import nl.novi.recipeapp.services.UserProfileRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user_profile_roles")
public class UserProfileRoleController {

    private final UserProfileRoleService userProfileRoleService;

    public UserProfileRoleController(
            UserProfileRoleService userRoleService) {
        this.userProfileRoleService = userRoleService;
    }

    @GetMapping
    public ResponseEntity<List<UserProfileRoleResponseDto>> getAllUserRoles() {
        return ResponseEntity.ok(
                userProfileRoleService.getAllUserRoles()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileRoleResponseDto> getUserRoleById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                userProfileRoleService.getUserRoleById(id)
        );
    }

    @PostMapping
    public ResponseEntity<UserProfileRoleResponseDto> createUserRole(
            @Valid @RequestBody UserProfileRoleRequestDto requestDto ) {

        UserProfileRoleResponseDto createdUserRole =
                userProfileRoleService.createUserRole(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUserRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileRoleResponseDto> updateUserRole(
            @PathVariable Long id,
            @Valid @RequestBody UserProfileRoleRequestDto requestDto) {

        return ResponseEntity.ok(
                userProfileRoleService.updateUserRole(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(
            @PathVariable Long id) {

        userProfileRoleService.deleteUserRole(id);

        return ResponseEntity.noContent().build();
    }
}
