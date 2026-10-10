package nl.novi.recipeapp.services;

import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    public UserProfileService(
            UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public List<UserProfileEntity> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    public UserProfileEntity getUserProfileById(Long id) {
        return userProfileRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Gebruikersprofiel niet gevonden met ID: " + id
                        )
                );
    }

    public UserProfileEntity createUserProfile(
            UserProfileEntity userProfile) {
        return userProfileRepository.save(userProfile);
    }

    public UserProfileEntity updateUserProfile(
            Long id,
            UserProfileEntity userProfile) {

        UserProfileEntity existingUserProfile =
                getUserProfileById(id);

        existingUserProfile.setUsername(userProfile.getUsername());
        existingUserProfile.setEmail(userProfile.getEmail());
        existingUserProfile.setPassword(userProfile.getPassword());
        existingUserProfile.setPasswordResetToken(
                userProfile.getPasswordResetToken()
        );
        existingUserProfile.setProfilePhotoUrl(
                userProfile.getProfilePhotoUrl()
        );
        existingUserProfile.setBio(userProfile.getBio());

        return userProfileRepository.save(existingUserProfile);
    }

    public void deleteUserProfile(Long id) {
        UserProfileEntity existingUserProfile =
                getUserProfileById(id);

        userProfileRepository.delete(existingUserProfile);
    }
}
