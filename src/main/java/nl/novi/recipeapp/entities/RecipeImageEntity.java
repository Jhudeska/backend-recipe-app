package nl.novi.recipeapp.entities;

import jakarta.persistence.*;

@Entity
@Table( name = "recipe_images")
public class RecipeImageEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_image_id")
    private Long recipeImageId;

    @Column(name = "image_url", nullable = false, length = 2048)
    private String imageUrl;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "recipe_id",
            nullable = false,
            unique = true
    )
    private RecipeEntity recipe;

    public RecipeImageEntity() {
    }

    public RecipeImageEntity(String imageUrl, RecipeEntity recipe) {
        this.imageUrl = imageUrl;
        this.recipe = recipe;
    }

    public Long getRecipeImageId() {
        return recipeImageId;
    }

    public void setRecipeImageId(Long recipeImageId) {
        this.recipeImageId = recipeImageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }
}
