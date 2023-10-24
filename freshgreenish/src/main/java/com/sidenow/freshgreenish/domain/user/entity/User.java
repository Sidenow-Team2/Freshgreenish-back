
package com.sidenow.freshgreenish.domain.user.entity;

import com.sidenow.freshgreenish.global.utils.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
@Table(name = "USERS")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long userId;

    @Column(length = 20)
    private String email;

    @Column(length = 20)
    @Setter
    private String nickname;

    @Setter
    private Integer saved_money;

    @Setter
    private Boolean isJoinRegular;

    @Column(length = 20)
    private String socialType;

    @Column(length = 2000)
    @Setter
    private String filePath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String email, String password, String nickname, int saved_money, Boolean isJoinRegular, String socialType, String filePath, Role role) {
        this.email = email;
        this.nickname = nickname;
        this.saved_money = saved_money;
        this.isJoinRegular = isJoinRegular;
        this.socialType = socialType;
        this.filePath = filePath;
        this.role = role;
    }


    public User update(String nickname, String filePath) {
        this.nickname = nickname;
        this.filePath = filePath;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }

}



