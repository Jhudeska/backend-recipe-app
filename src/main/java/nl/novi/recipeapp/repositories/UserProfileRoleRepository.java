package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.UserProfileRoleEntity;
import nl.novi.recipeapp.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRoleRepository extends JpaRepository<UserProfileRoleEntity, Long> {
    boolean existsByUserProfile_UserIdAndRole(Long userId, Role role);
}
