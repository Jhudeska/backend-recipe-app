package nl.novi.recipeapp.services;


import nl.novi.recipeapp.dtos.reviewrating.ReviewRatingRequestDto;
import nl.novi.recipeapp.dtos.reviewrating.ReviewRatingResponseDto;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.ReviewRatingEntity;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.exceptions.ResourceNotFoundException;
import nl.novi.recipeapp.mappers.ReviewRatingMapper;
import nl.novi.recipeapp.repositories.RecipeRepository;
import nl.novi.recipeapp.repositories.ReviewRatingRepository;
import nl.novi.recipeapp.repositories.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ReviewRatingService {

    private final ReviewRatingRepository reviewRatingRepository;
    private final UserProfileRepository userProfileRepository;
    private final RecipeRepository recipeRepository;
    private final ReviewRatingMapper reviewRatingMapper;

    public ReviewRatingService(
            ReviewRatingRepository reviewRatingRepository,
            UserProfileRepository userProfileRepository,
            RecipeRepository recipeRepository, ReviewRatingMapper reviewRatingMapper) {
        this.reviewRatingRepository = reviewRatingRepository;
        this.userProfileRepository = userProfileRepository;
        this.recipeRepository = recipeRepository;
        this.reviewRatingMapper = reviewRatingMapper;
    }

    @Transactional(readOnly = true)
    public List<ReviewRatingResponseDto> getAllReviewRatings() {
        return reviewRatingRepository.findAll()
                .stream()
                .map(reviewRatingMapper::toResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReviewRatingResponseDto getReviewRatingById(Long id) {
        return reviewRatingMapper.toResponseDto(findReviewRatingById(id));
    }

    @Transactional
    public ReviewRatingResponseDto createReviewRating(
            Long userId,
            ReviewRatingRequestDto requestDto) {

        UserProfileEntity userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Gebruiker niet gevonden met ID: " + userId
                ));

        RecipeEntity recipe = recipeRepository.findById(
                requestDto.recipeId()
        ).orElseThrow(() -> new ResourceNotFoundException(
                "Recept niet gevonden met ID: " + requestDto.recipeId()
        ));

        ReviewRatingEntity reviewRating = new ReviewRatingEntity(
                requestDto.comment(),
                requestDto.ratingScore(),
                userProfile,
                recipe
        );

        ReviewRatingEntity savedReviewRating = reviewRatingRepository.save(reviewRating);

        return reviewRatingMapper.toResponseDto(savedReviewRating);
    }

    public ReviewRatingResponseDto updateReviewRating(
            Long id,
            ReviewRatingRequestDto requestDto) {

        ReviewRatingEntity existingReviewRating =
                findReviewRatingById(id);

        UserProfileEntity userProfile = userProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Gebruiker niet gevonden met ID: " + id
                ));

        RecipeEntity recipe = recipeRepository.findById(
                requestDto.recipeId()
        ).orElseThrow(() -> new ResourceNotFoundException(
                "Recept niet gevonden met ID: " + requestDto.recipeId()
        ));


        existingReviewRating.setComment(requestDto.comment());
        existingReviewRating.setRatingScore(
                requestDto.ratingScore()
        );

        existingReviewRating.setUserProfile(userProfile);
        existingReviewRating.setRecipe(recipe);

        ReviewRatingEntity savedReviewRating = reviewRatingRepository.save(existingReviewRating);

        return reviewRatingMapper.toResponseDto(savedReviewRating);
    }

    public void deleteReviewRating(Long id) {
        ReviewRatingEntity existingReviewRating =
                findReviewRatingById(id);

        reviewRatingRepository.delete(existingReviewRating);
    }

    private ReviewRatingEntity findReviewRatingById(Long id) {

        return reviewRatingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Review niet gevonden met ID: " + id
                ));
    }
}
