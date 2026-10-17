package nl.novi.recipeapp.entities;

import jakarta.persistence.*;
import nl.novi.recipeapp.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_profiles")
public class UserProfileEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "password_reset_token")
    private String passwordResetToken;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @OneToMany(mappedBy = "userProfile")
    private List<ReviewRatingEntity> reviews = new ArrayList<>();

    @OneToMany(
            mappedBy = "userProfile",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<UserProfileRoleEntity> roles = new ArrayList<>();

    @OneToMany(mappedBy = "userProfile")
    private List<FavoriteEntity> favorites = new ArrayList<>();


    public UserProfileEntity() {
    }

    public UserProfileEntity(
            String username,
            String email,
            String password,
            String passwordResetToken,
            String profilePhotoUrl,
            String bio) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.passwordResetToken = passwordResetToken;
        this.profilePhotoUrl = profilePhotoUrl;
        this.bio = bio;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordResetToken() {
        return passwordResetToken;
    }

    public void setPasswordResetToken(String passwordResetToken) {
        this.passwordResetToken = passwordResetToken;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<ReviewRatingEntity> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewRatingEntity> reviews) {
        this.reviews = reviews;
    }

    public List<UserProfileRoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(List<UserProfileRoleEntity> roles) {
        this.roles = roles;
    }

    public void setFavorites(List<FavoriteEntity> favorites) {
        this.favorites = favorites;
    }

    public void addRole(Role role) {
        UserProfileRoleEntity userRole = new UserProfileRoleEntity();
        userRole.setRole(role);
        userRole.setUserProfile(this);
        roles.add(userRole);
    }

//    public void removeRole(UserProfileRoleEntity role) {
//        roles.remove(role);
//        role.setUserProfile(null);
//    }
}
