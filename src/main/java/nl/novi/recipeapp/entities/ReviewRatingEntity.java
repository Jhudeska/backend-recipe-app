package nl.novi.recipeapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "review_ratings")
public class ReviewRatingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_rating_id")
    private Long reviewRatingId;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Min(1)
    @Max(5)
    @Column(name = "ratingscore", nullable = false)
    private Integer ratingScore;

    @ManyToOne(fetch = FetchType.LAZY , optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfileEntity userProfile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false)
    private RecipeEntity recipe;

    public ReviewRatingEntity() {
    }

    public ReviewRatingEntity(
            String comment,
            Integer ratingScore,
            UserProfileEntity userProfile,
            RecipeEntity recipe) {
        this.comment = comment;
        this.ratingScore = ratingScore;
        this.userProfile = userProfile;
        this.recipe = recipe;
    }

    public Long getReviewRatingId() {
        return reviewRatingId;
    }

    public void setReviewRatingId(Long reviewRatingId) {
        this.reviewRatingId = reviewRatingId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(Integer ratingScore) {
        this.ratingScore = ratingScore;
    }



    public UserProfileEntity getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileEntity userProfile) {
        this.userProfile = userProfile;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }
}
