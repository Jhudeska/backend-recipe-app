package nl.novi.recipeapp.controllers;

import jakarta.validation.Valid;
import nl.novi.recipeapp.dtos.userprofile.UserProfileRequestDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileResponseDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileUpdateDto;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.services.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<List<UserProfileResponseDto>> getAllUserProfiles() {
        return ResponseEntity.ok(
                userProfileService.getAllUserProfiles()
        );
    }

    @GetMapping("/{id}")
//    @PreAuthorize("@securityHelper.isOwner(authentication.principal, #username || hasrole('ADMIN'))")
    public ResponseEntity<UserProfileResponseDto> getUserProfileById(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                userProfileService.getUserProfileById(id)
        );
    }

    @PostMapping
    public ResponseEntity<UserProfileResponseDto> createUserProfile(
            @Valid @RequestBody UserProfileRequestDto requestDto) {

        UserProfileResponseDto createdUserProfile =
                userProfileService.createUserProfile(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdUserProfile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfileResponseDto> updateUserProfile(
            @PathVariable Long id,
            @Valid @RequestBody UserProfileUpdateDto requestDto) {

        return ResponseEntity.ok(
                userProfileService.updateUserProfile(id, requestDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserProfile(
            @PathVariable Long id) {

        userProfileService.deleteUserProfile(id);

        return ResponseEntity.noContent().build();
    }
}
