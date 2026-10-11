package nl.novi.recipeapp.repositories;

import nl.novi.recipeapp.entities.UserProfileRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRoleRepository extends JpaRepository<UserProfileRoleEntity, Long> {
}
