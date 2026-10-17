package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.userprofilerole.UserProfileRoleRequestDto;
import nl.novi.recipeapp.dtos.userprofilerole.UserProfileRoleResponseDto;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.entities.UserProfileRoleEntity;
import nl.novi.recipeapp.mappers.UserProfileRoleMapper;
import nl.novi.recipeapp.repositories.UserProfileRepository;
import nl.novi.recipeapp.repositories.UserProfileRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserProfileRoleService {
    private final UserProfileRoleRepository userRoleRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileRoleMapper userRoleMapper;

    public UserProfileRoleService(
            UserProfileRoleRepository userRoleRepository, UserProfileRepository userProfileRepository, UserProfileRoleMapper userRoleMapper) {
        this.userRoleRepository = userRoleRepository;
        this.userProfileRepository = userProfileRepository;
        this.userRoleMapper = userRoleMapper;
    }

    @Transactional(readOnly = true)
    public List<UserProfileRoleResponseDto> getAllUserRoles() {

        return userRoleRepository.findAll()
                .stream()
                .map(userRoleMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public UserProfileRoleResponseDto getUserRoleById(Long id) {
        return userRoleMapper.toResponseDto(findUserRoleById(id));
    }

    public UserProfileRoleResponseDto createUserRole(
            UserProfileRoleRequestDto requestDto) {

        UserProfileEntity userProfile =
                userProfileRepository.findById(requestDto.userId())
                        .orElseThrow(() -> new RuntimeException(
                                "User not found with ID: "
                                        + requestDto.userId()
                        ));

        if (userRoleRepository.existsByUserProfile_UserIdAndRole(
                requestDto.userId(), requestDto.role())) {
            throw new IllegalArgumentException(
                    "This user already has the role: "
                            + requestDto.role()
            );
        }

        UserProfileRoleEntity userRole = new UserProfileRoleEntity(
                requestDto.role(),
                userProfile
        );
        UserProfileRoleEntity savedUserRole = userRoleRepository.save(userRole);

        return userRoleMapper.toResponseDto(savedUserRole);
    }

    public UserProfileRoleResponseDto updateUserRole(
            Long id,
            UserProfileRoleRequestDto requestDto) {

        UserProfileRoleEntity existingUserRole =
                findUserRoleById(id);

        UserProfileEntity userProfile =
                userProfileRepository.findById(requestDto.userId())
                        .orElseThrow(() -> new RuntimeException(
                                "User not found with ID: "
                                        + requestDto.userId()
                        ));

        boolean roleChanged =
                !existingUserRole.getRole().equals(requestDto.role())
                        || !existingUserRole.getUserProfile()
                        .getUserId().equals(requestDto.userId());

        if (roleChanged
                && userRoleRepository.existsByUserProfile_UserIdAndRole(
                requestDto.userId(), requestDto.role())) {
            throw new IllegalArgumentException(
                    "This user already has the role: "
                            + requestDto.role()
            );
        }

        existingUserRole.setRole(requestDto.role());
        existingUserRole.setUserProfile(userProfile);

        UserProfileRoleEntity savedUserRole = userRoleRepository.save(existingUserRole);
        return userRoleMapper.toResponseDto(savedUserRole);
    }

    public void deleteUserRole(Long id) {
        UserProfileRoleEntity userRole = findUserRoleById(id);
        userRoleRepository.delete(userRole);
    }

    private UserProfileRoleEntity findUserRoleById(Long id) {

        return userRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "User role not found with ID: " + id
                ));
    }

}
