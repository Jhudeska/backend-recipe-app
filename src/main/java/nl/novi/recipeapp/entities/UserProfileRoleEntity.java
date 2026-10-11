package nl.novi.recipeapp.entities;

import jakarta.persistence.*;

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

}
