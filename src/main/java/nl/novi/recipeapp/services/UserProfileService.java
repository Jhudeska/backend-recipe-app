package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.userprofile.UserProfileRequestDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileResponseDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileUpdateDto;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.enums.Role;
import nl.novi.recipeapp.exceptions.ResourceAlreadyExistsException;
import nl.novi.recipeapp.exceptions.ResourceNotFoundException;
import nl.novi.recipeapp.mappers.UserProfileMapper;
import nl.novi.recipeapp.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.jwt.Jwt;

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

//    public UserProfileResponseDto updateUserProfile(
//            Long id,
//            UserProfileUpdateDto requestDto) {
//
//        UserProfileEntity existingUserProfile =
//                findUserProfileById(id);
//
//        String username = requestDto.username().trim();
//        String email = requestDto.email().trim().toLowerCase();
//
//        boolean usernameChanged =
//                !existingUserProfile.getUsername().equalsIgnoreCase(username);
//
//        boolean emailChanged =
//                !existingUserProfile.getEmail().equalsIgnoreCase(email);
//
//        if (usernameChanged
//                && userProfileRepository.existsByUsernameIgnoreCase(username)) {
//            throw new ResourceAlreadyExistsException(
//                    "Username already exists: " + username
//            );
//        }
//
//        if (emailChanged
//                && userProfileRepository.existsByEmailIgnoreCase(email)) {
//            throw new ResourceAlreadyExistsException(
//                    "Email already exists: " + email
//            );
//        }
//
//        existingUserProfile.setUsername(username);
//        existingUserProfile.setEmail(email);
//        existingUserProfile.setProfilePhotoUrl(
//                requestDto.profilePhotoUrl()
//        );
//        existingUserProfile.setBio(requestDto.bio());
//
//
//        UserProfileEntity savedUserProfile = userProfileRepository.save(existingUserProfile);
//
//        return userProfileMapper.toResponseDto(savedUserProfile);
//    }


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

    @Transactional
    public UserProfileEntity getOrCreateUserProfile(Jwt jwt) {

        String keycloakId = jwt.getSubject();

        return userProfileRepository.findByKeycloakId(keycloakId)
                .orElseGet(() -> {

                    String username =
                            jwt.getClaimAsString("preferred_username");

                    String email =
                            jwt.getClaimAsString("email");

                    if (username == null || username.isBlank()
                            || email == null || email.isBlank()) {
                        throw new IllegalArgumentException(
                                "Keycloak username or email is missing"
                        );
                    }

                    username = username.trim();
                    email = email.trim().toLowerCase();

                    if (userProfileRepository
                            .existsByUsernameIgnoreCase(username)) {
                        throw new ResourceAlreadyExistsException(
                                "Username already exists: " + username
                        );
                    }

                    if (userProfileRepository
                            .existsByEmailIgnoreCase(email)) {
                        throw new ResourceAlreadyExistsException(
                                "Email already exists: " + email
                        );
                    }

                    UserProfileEntity user = new UserProfileEntity(
                            username,
                            email,
                            null,
                            null
//                            null,
//                            null
                    );



                    user.setKeycloakId(keycloakId);

                    user.addRole(Role.USER);

                    System.out.println("Keycloak ID: " + keycloakId);
                    System.out.println("Username: " +
                            jwt.getClaimAsString("preferred_username"));
                    System.out.println("Email: " +
                            jwt.getClaimAsString("email"));

                    return userProfileRepository.save(user);
                });
    }

    @Transactional
    public UserProfileResponseDto getCurrentUserProfile(Jwt jwt) {

        UserProfileEntity user = getOrCreateUserProfile(jwt);

        return userProfileMapper.toResponseDto(user);
    }

//    @Transactional
//    public UserProfileResponseDto getCurrentUserProfile(Jwt jwt) {
//
//        System.out.println("ME: service gestart");
//
//        UserProfileEntity user = getOrCreateUserProfile(jwt);
//
//        System.out.println("ME: gebruiker opgehaald: " + user.getUserId());
//
//        UserProfileResponseDto response =
//                userProfileMapper.toResponseDto(user);
//
//        System.out.println("ME: DTO aangemaakt");
//
//        return response;
//    }


//    @Transactional
//    public UserProfileResponseDto updateCurrentUserProfile(
//            Jwt jwt,
//            UserProfileUpdateDto requestDto) {
//
//        // Haal het profiel op aan de hand van het Keycloak ID.
//        UserProfileEntity existingUserProfile =
//                getOrCreateUserProfile(jwt);
//
//        String username = requestDto.username().trim();
//        String email = requestDto.email().trim().toLowerCase();
//
//        boolean usernameChanged =
//                !existingUserProfile.getUsername()
//                        .equalsIgnoreCase(username);
//
//        boolean emailChanged =
//                !existingUserProfile.getEmail()
//                        .equalsIgnoreCase(email);
//
//        // Controleer of username al bestaat.
//        if (usernameChanged &&
//                userProfileRepository.existsByUsernameIgnoreCase(username)) {
//
//            throw new ResourceAlreadyExistsException(
//                    "Username already exists: " + username
//            );
//        }
//
//        // Controleer of email al bestaat.
//        if (emailChanged &&
//                userProfileRepository.existsByEmailIgnoreCase(email)) {
//
//            throw new ResourceAlreadyExistsException(
//                    "Email already exists: " + email
//            );
//        }
//
//        // Profielgegevens aanpassen.
//        existingUserProfile.setUsername(username);
//        existingUserProfile.setEmail(email);
//        existingUserProfile.setProfilePhotoUrl(
//                requestDto.profilePhotoUrl()
//        );
//        existingUserProfile.setBio(
//                requestDto.bio()
//        );
//
//        UserProfileEntity savedUserProfile =
//                userProfileRepository.save(existingUserProfile);
//
//        return userProfileMapper.toResponseDto(savedUserProfile);
//    }


    public UserProfileResponseDto updateUserProfile(
            Long id,
            UserProfileUpdateDto requestDto) {

        UserProfileEntity existingUserProfile = findUserProfileById(id);

        updateProfileFields(existingUserProfile, requestDto);

        UserProfileEntity savedUserProfile =
                userProfileRepository.save(existingUserProfile);

        return userProfileMapper.toResponseDto(savedUserProfile);
    }

    @Transactional
    public UserProfileResponseDto updateCurrentUserProfile(
            Jwt jwt,
            UserProfileUpdateDto requestDto) {

        UserProfileEntity existingUserProfile = getOrCreateUserProfile(jwt);

        updateProfileFields(existingUserProfile, requestDto);

        UserProfileEntity savedUserProfile =
                userProfileRepository.save(existingUserProfile);

        return userProfileMapper.toResponseDto(savedUserProfile);
    }

    private void updateProfileFields(
            UserProfileEntity existingUserProfile,
            UserProfileUpdateDto requestDto) {

        String email = requestDto.email().trim().toLowerCase();

        boolean emailChanged =
                !existingUserProfile.getEmail().equalsIgnoreCase(email);

        if (emailChanged &&
                userProfileRepository.existsByEmailIgnoreCase(email)) {

            throw new ResourceAlreadyExistsException(
                    "Email already exists: " + email
            );
        }

        // Username wordt bewust niet gewijzigd.
        existingUserProfile.setEmail(email);

        existingUserProfile.setProfilePhotoUrl(
                requestDto.profilePhotoUrl()
        );

        existingUserProfile.setBio(
                requestDto.bio()
        );
    }

}
