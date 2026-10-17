package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.userprofilerole.UserProfileRoleResponseDto;
import nl.novi.recipeapp.entities.UserProfileRoleEntity;
import org.springframework.stereotype.Component;

@Component
public class UserProfileRoleMapper {

    public UserProfileRoleResponseDto toResponseDto(UserProfileRoleEntity userRole) {

        return new UserProfileRoleResponseDto(
                userRole.getUserProfileRoleId(),
                userRole.getRole(),
                userRole.getUserProfile().getUserId(),
                userRole.getCreated_at(),
                userRole.getUpdated_at()
        );
    }
}
