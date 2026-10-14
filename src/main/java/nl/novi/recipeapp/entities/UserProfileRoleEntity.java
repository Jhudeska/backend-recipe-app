package nl.novi.recipeapp.entities;

import jakarta.persistence.*;
import nl.novi.recipeapp.enums.Role;

@Entity
@Table(name = "user_profile_roles")
public class UserProfileRoleEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_role_id")
    private Long userProfileRoleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 50)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfileEntity userProfile;

    public UserProfileRoleEntity() {
    }

    public Long getUserProfileRoleId() {
        return userProfileRoleId;
    }

    public void setUserProfileRoleId(Long userProfileRoleId) {
        this.userProfileRoleId = userProfileRoleId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserProfileEntity getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfileEntity userProfile) {
        this.userProfile = userProfile;
    }
}
