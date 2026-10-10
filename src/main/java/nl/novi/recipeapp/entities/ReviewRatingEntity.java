package nl.novi.recipeapp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "review_ratings")
public class ReviewRatingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_rating_id")
    private Long reviewRatingId;

    @Column(name = "comment")
    private String comment;

    @Column(name = "ratingscore", nullable = false)
    private Integer ratingScore;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    public ReviewRatingEntity() {
    }

    public ReviewRatingEntity(
            String comment,
            Integer ratingScore,
            Long userId,
            Long recipeId) {
        this.comment = comment;
        this.ratingScore = ratingScore;
        this.userId = userId;
        this.recipeId = recipeId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }
}
