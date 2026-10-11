package nl.novi.recipeapp.services;

import nl.novi.recipeapp.entities.UserProfileRoleEntity;
import nl.novi.recipeapp.repositories.UserProfileRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileRoleService {
    private final UserProfileRoleRepository userRoleRepository;

    public UserProfileRoleService(
            UserProfileRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserProfileRoleEntity> getAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public UserProfileRoleEntity getUserRoleById(Long id) {
        return userRoleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "UserRole niet gevonden met id: " + id
                        )
                );
    }

    public UserProfileRoleEntity createUserRole(
            UserProfileRoleEntity userRole) {
        return userRoleRepository.save(userRole);
    }

    public UserProfileRoleEntity updateUserRole(
            Long id,
            UserProfileRoleEntity updatedUserRole) {

        UserProfileRoleEntity existingUserRole =
                getUserRoleById(id);

        existingUserRole.setRole(updatedUserRole.getRole());

        return userRoleRepository.save(existingUserRole);
    }

    public void deleteUserRole(Long id) {
        UserProfileRoleEntity userRole = getUserRoleById(id);
        userRoleRepository.delete(userRole);
    }

}
