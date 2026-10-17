package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.UserProfileRoleDto;
import nl.novi.recipeapp.entities.UserProfileRoleEntity;
import org.springframework.stereotype.Component;

@Component
public class UserProfileRoleMapper {

    public UserProfileRoleDto toDto(UserProfileRoleEntity userRole) {

        return new UserProfileRoleDto(
                userRole.getUserProfileRoleId(),
                userRole.getRole(),
                userRole.getUserProfile().getUserId()
        );
    }
}
