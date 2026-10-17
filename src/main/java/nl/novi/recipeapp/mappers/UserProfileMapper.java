package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.userprofile.UserProfileDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileRequestDto;
import nl.novi.recipeapp.entities.UserProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileDto toDto(UserProfileEntity user) {

        return new UserProfileDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfilePhotoUrl(),
                user.getBio()
        );
    }

    public UserProfileEntity toEntity(UserProfileRequestDto dto) {

        return new UserProfileEntity(
                dto.username(),
                dto.email(),
                dto.password(),
                null,
                dto.profilePhotoUrl(),
                dto.bio()
        );
    }
}



