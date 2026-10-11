package nl.novi.recipeapp.controllers;

import nl.novi.recipeapp.entities.UserProfileRoleEntity;
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
    public ResponseEntity<List<UserProfileRoleEntity>> getAllUserRoles() {
        return ResponseEntity.ok(
                userProfileRoleService.getAllUserRoles()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileRoleEntity> getUserRoleById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                userProfileRoleService.getUserRoleById(id)
        );
    }

    @PostMapping
    public ResponseEntity<UserProfileRoleEntity> createUserRole(
            @RequestBody UserProfileRoleEntity userRole) {

        UserProfileRoleEntity createdUserRole =
                userProfileRoleService.createUserRole(userRole);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUserRole);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileRoleEntity> updateUserRole(
            @PathVariable Long id,
            @RequestBody UserProfileRoleEntity userRole) {

        return ResponseEntity.ok(
                userProfileRoleService.updateUserRole(id, userRole)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserRole(
            @PathVariable Long id) {

        userProfileRoleService.deleteUserRole(id);

        return ResponseEntity.noContent().build();
    }
}
