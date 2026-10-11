package nl.novi.recipeapp.controllers;

import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.services.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user_profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    public UserProfileController(
            UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping
    public ResponseEntity<List<UserProfileEntity>> getAllUserProfiles() {
        return ResponseEntity.ok(
                userProfileService.getAllUserProfiles()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileEntity> getUserProfileById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                userProfileService.getUserProfileById(id)
        );
    }

    @PostMapping
    public ResponseEntity<UserProfileEntity> createUserProfile(
            @RequestBody UserProfileEntity userProfile) {

        UserProfileEntity createdUserProfile =
                userProfileService.createUserProfile(userProfile);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUserProfile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileEntity> updateUserProfile(
            @PathVariable Long id,
            @RequestBody UserProfileEntity userProfile) {

        return ResponseEntity.ok(
                userProfileService.updateUserProfile(id, userProfile)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(
            @PathVariable Long id) {

        userProfileService.deleteUserProfile(id);

        return ResponseEntity.noContent().build();
    }
}
