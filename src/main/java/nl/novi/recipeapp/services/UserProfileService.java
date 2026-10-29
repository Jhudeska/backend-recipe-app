package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.userprofile.UserProfileRequestDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileResponseDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileUpdateDto;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.exceptions.ResourceAlreadyExistsException;
import nl.novi.recipeapp.exceptions.ResourceNotFoundException;
import nl.novi.recipeapp.mappers.UserProfileMapper;
import nl.novi.recipeapp.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileService(
            UserProfileRepository userProfileRepository, UserProfileMapper userProfileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userProfileMapper = userProfileMapper;
    }

    @Transactional(readOnly = true)
    public List<UserProfileResponseDto> getAllUserProfiles() {
        return userProfileRepository.findAll()
                .stream()
                .map(userProfileMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserProfileResponseDto getUserProfileById(Long id) {
        return userProfileMapper.toResponseDto(findUserProfileById(id));
    }

    public UserProfileResponseDto createUserProfile(
            UserProfileRequestDto requestDto) {

        String username = requestDto.username().trim();
        String email = requestDto.email().trim().toLowerCase();

        if (userProfileRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResourceAlreadyExistsException(
                    "Username already exists: " + username
            );
        }

        if (userProfileRepository.existsByEmailIgnoreCase(email)) {
            throw new ResourceAlreadyExistsException(
                    "Email already exists: " + email
            );
        }

        UserProfileEntity userProfile =
                userProfileMapper.toEntity(requestDto);
        UserProfileEntity savedUserProfile = userProfileRepository.save(userProfile);

        return userProfileMapper.toResponseDto(savedUserProfile);
    }

    public UserProfileResponseDto updateUserProfile(
            Long id,
            UserProfileUpdateDto requestDto) {

        UserProfileEntity existingUserProfile =
                findUserProfileById(id);

        String username = requestDto.username().trim();
        String email = requestDto.email().trim().toLowerCase();

        boolean usernameChanged =
                !existingUserProfile.getUsername().equalsIgnoreCase(username);

        boolean emailChanged =
                !existingUserProfile.getEmail().equalsIgnoreCase(email);

        if (usernameChanged
                && userProfileRepository.existsByUsernameIgnoreCase(username)) {
            throw new ResourceAlreadyExistsException(
                    "Username already exists: " + username
            );
        }

        if (emailChanged
                && userProfileRepository.existsByEmailIgnoreCase(email)) {
            throw new ResourceAlreadyExistsException(
                    "Email already exists: " + email
            );
        }

        existingUserProfile.setUsername(username);
        existingUserProfile.setEmail(email);
//        existingUserProfile.setPassword(requestDto.password);
//        existingUserProfile.setPasswordResetToken(
//                requestDto.passwordResetToken()
//        );
        existingUserProfile.setProfilePhotoUrl(
                requestDto.profilePhotoUrl()
        );
        existingUserProfile.setBio(requestDto.bio());


        UserProfileEntity savedUserProfile = userProfileRepository.save(existingUserProfile);

        return userProfileMapper.toResponseDto(savedUserProfile);
    }

    public void deleteUserProfile(Long id) {
        UserProfileEntity existingUserProfile =
                findUserProfileById(id);
        userProfileRepository.delete(existingUserProfile);
    }

    private UserProfileEntity findUserProfileById(Long id) {

        return userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User profile not found with ID: " + id
                ));
    }
}
