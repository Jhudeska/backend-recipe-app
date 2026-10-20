package nl.novi.recipeapp.mappers;

import nl.novi.recipeapp.dtos.userprofile.UserProfileDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileRequestDto;
import nl.novi.recipeapp.dtos.userprofile.UserProfileResponseDto;
import nl.novi.recipeapp.entities.UserProfileEntity;
import org.springframework.stereotype.Component;

@Component
public class UserProfileMapper {

    public UserProfileResponseDto toResponseDto(UserProfileEntity user) {

        return new UserProfileResponseDto(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getProfilePhotoUrl(),
                user.getBio(),
                user.getCreated_at(),
                user.getUpdated_at()
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



