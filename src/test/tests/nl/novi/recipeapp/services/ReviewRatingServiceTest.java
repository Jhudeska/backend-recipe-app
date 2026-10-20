package nl.novi.recipeapp.services;

import nl.novi.recipeapp.dtos.reviewrating.ReviewRatingRequestDto;
import nl.novi.recipeapp.dtos.reviewrating.ReviewRatingResponseDto;
import nl.novi.recipeapp.entities.RecipeEntity;
import nl.novi.recipeapp.entities.ReviewRatingEntity;
import nl.novi.recipeapp.entities.UserProfileEntity;
import nl.novi.recipeapp.mappers.ReviewRatingMapper;
import nl.novi.recipeapp.repositories.RecipeRepository;
import nl.novi.recipeapp.repositories.ReviewRatingRepository;
import nl.novi.recipeapp.repositories.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewRatingServiceTest {

    @Mock
    private ReviewRatingRepository reviewRatingRepository;

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private ReviewRatingMapper reviewRatingMapper;

    @InjectMocks
    private ReviewRatingService reviewRatingService;

    private ReviewRatingEntity reviewRating;
    private UserProfileEntity userProfile;
    private RecipeEntity recipe;
    private ReviewRatingRequestDto requestDto;
    private ReviewRatingResponseDto responseDto;

    @BeforeEach
    void setUp() {
        reviewRating = mock(ReviewRatingEntity.class);
        userProfile = mock(UserProfileEntity.class);
        recipe = mock(RecipeEntity.class);
        requestDto = mock(ReviewRatingRequestDto.class);
        responseDto = mock(ReviewRatingResponseDto.class);
    }

    // GET ALL

    @Test
    void getAllReviewRatingsReturnsMappedDtos() {
        ReviewRatingEntity secondReview = mock(ReviewRatingEntity.class);
        ReviewRatingResponseDto secondResponse =
                mock(ReviewRatingResponseDto.class);

        when(reviewRatingRepository.findAll())
                .thenReturn(List.of(reviewRating, secondReview));
        when(reviewRatingMapper.toResponseDto(reviewRating))
                .thenReturn(responseDto);
        when(reviewRatingMapper.toResponseDto(secondReview))
                .thenReturn(secondResponse);

        List<ReviewRatingResponseDto> result =
                reviewRatingService.getAllReviewRatings();

        assertEquals(2, result.size());
        assertSame(responseDto, result.get(0));
        assertSame(secondResponse, result.get(1));

        verify(reviewRatingMapper).toResponseDto(reviewRating);
        verify(reviewRatingMapper).toResponseDto(secondReview);
    }

    @Test
    void getAllReviewRatingsReturnsEmptyList() {
        when(reviewRatingRepository.findAll()).thenReturn(List.of());

        List<ReviewRatingResponseDto> result =
                reviewRatingService.getAllReviewRatings();

        assertTrue(result.isEmpty());
        verify(reviewRatingRepository).findAll();
        verifyNoInteractions(reviewRatingMapper);
    }

    // GET BY ID

    @Test
    void getReviewRatingByIdReturnsMappedDto() {
        when(reviewRatingRepository.findById(1L))
                .thenReturn(Optional.of(reviewRating));
        when(reviewRatingMapper.toResponseDto(reviewRating))
                .thenReturn(responseDto);

        ReviewRatingResponseDto result =
                reviewRatingService.getReviewRatingById(1L);

        assertSame(responseDto, result);
        verify(reviewRatingRepository).findById(1L);
        verify(reviewRatingMapper).toResponseDto(reviewRating);
    }

    @Test
    void getReviewRatingByIdThrowsWhenNotFound() {
        when(reviewRatingRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reviewRatingService.getReviewRatingById(99L)
        );

        assertEquals(
                "Review niet gevonden met ID: 99",
                exception.getMessage()
        );

        verifyNoInteractions(reviewRatingMapper);
    }

    // CREATE

    @Test
    void createReviewRatingSavesAndReturnsDto() {
        when(requestDto.recipeId()).thenReturn(10L);
        when(requestDto.comment()).thenReturn("Heerlijk recept");
        when(requestDto.ratingScore()).thenReturn(5);

        when(userProfileRepository.findById(1L))
                .thenReturn(Optional.of(userProfile));
        when(recipeRepository.findById(10L))
                .thenReturn(Optional.of(recipe));
        when(reviewRatingRepository.save(any(ReviewRatingEntity.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(reviewRatingMapper.toResponseDto(any(ReviewRatingEntity.class)))
                .thenReturn(responseDto);

        ReviewRatingResponseDto result =
                reviewRatingService.createReviewRating(1L, requestDto);

        assertSame(responseDto, result);

        verify(reviewRatingRepository).save(argThat(saved ->
                "Heerlijk recept".equals(saved.getComment())
                        && Integer.valueOf(5).equals(saved.getRatingScore())
                        && saved.getUserProfile() == userProfile
                        && saved.getRecipe() == recipe
        ));

        verify(reviewRatingMapper).toResponseDto(any(ReviewRatingEntity.class));
    }

    @Test
    void createReviewRatingThrowsWhenUserNotFound() {
        when(userProfileRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reviewRatingService.createReviewRating(99L, requestDto)
        );

        assertEquals(
                "Gebruiker niet gevonden met ID: 99",
                exception.getMessage()
        );

        verifyNoInteractions(recipeRepository, reviewRatingRepository,
                reviewRatingMapper);
    }

    @Test
    void createReviewRatingThrowsWhenRecipeNotFound() {
        when(userProfileRepository.findById(1L))
                .thenReturn(Optional.of(userProfile));
        when(requestDto.recipeId()).thenReturn(99L);
        when(recipeRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reviewRatingService.createReviewRating(1L, requestDto)
        );

        assertEquals(
                "Recept niet gevonden met ID: 99",
                exception.getMessage()
        );

        verifyNoInteractions(reviewRatingRepository, reviewRatingMapper);
    }

    // UPDATE

    @Test
    void updateReviewRatingUpdatesAndReturnsDto() {
        when(reviewRatingRepository.findById(1L))
                .thenReturn(Optional.of(reviewRating));
        when(userProfileRepository.findById(1L))
                .thenReturn(Optional.of(userProfile));
        when(requestDto.recipeId()).thenReturn(10L);
        when(requestDto.comment()).thenReturn("Aangepaste review");
        when(requestDto.ratingScore()).thenReturn(4);
        when(recipeRepository.findById(10L))
                .thenReturn(Optional.of(recipe));
        when(reviewRatingRepository.save(reviewRating))
                .thenReturn(reviewRating);
        when(reviewRatingMapper.toResponseDto(reviewRating))
                .thenReturn(responseDto);

        ReviewRatingResponseDto result =
                reviewRatingService.updateReviewRating(1L, requestDto);

        assertSame(responseDto, result);

        verify(reviewRating).setComment("Aangepaste review");
        verify(reviewRating).setRatingScore(4);
        verify(reviewRating).setUserProfile(userProfile);
        verify(reviewRating).setRecipe(recipe);
        verify(reviewRatingRepository).save(reviewRating);
        verify(reviewRatingMapper).toResponseDto(reviewRating);
    }

    @Test
    void updateReviewRatingThrowsWhenReviewNotFound() {
        when(reviewRatingRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reviewRatingService.updateReviewRating(99L, requestDto)
        );

        assertEquals(
                "Review niet gevonden met ID: 99",
                exception.getMessage()
        );

        verifyNoInteractions(userProfileRepository, recipeRepository,
                reviewRatingMapper);
    }

    @Test
    void updateReviewRatingThrowsWhenUserNotFound() {
        when(reviewRatingRepository.findById(1L))
                .thenReturn(Optional.of(reviewRating));
        when(userProfileRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reviewRatingService.updateReviewRating(1L, requestDto)
        );

        assertEquals(
                "Gebruiker niet gevonden met ID: 1",
                exception.getMessage()
        );

        verify(reviewRatingRepository, never()).save(any());
        verifyNoInteractions(recipeRepository, reviewRatingMapper);
    }

    @Test
    void updateReviewRatingThrowsWhenRecipeNotFound() {
        when(reviewRatingRepository.findById(1L))
                .thenReturn(Optional.of(reviewRating));
        when(userProfileRepository.findById(1L))
                .thenReturn(Optional.of(userProfile));
        when(requestDto.recipeId()).thenReturn(99L);
        when(recipeRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reviewRatingService.updateReviewRating(1L, requestDto)
        );

        assertEquals(
                "Recept niet gevonden met ID: 99",
                exception.getMessage()
        );

        verify(reviewRatingRepository, never()).save(any());
        verifyNoInteractions(reviewRatingMapper);
    }

    // DELETE

    @Test
    void deleteReviewRatingDeletesExistingReview() {
        when(reviewRatingRepository.findById(1L))
                .thenReturn(Optional.of(reviewRating));

        reviewRatingService.deleteReviewRating(1L);

        verify(reviewRatingRepository).findById(1L);
        verify(reviewRatingRepository).delete(reviewRating);
    }

    @Test
    void deleteReviewRatingThrowsWhenNotFound() {
        when(reviewRatingRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> reviewRatingService.deleteReviewRating(99L)
        );

        assertEquals(
                "Review niet gevonden met ID: 99",
                exception.getMessage()
        );

        verify(reviewRatingRepository, never()).delete(any());
    }
}