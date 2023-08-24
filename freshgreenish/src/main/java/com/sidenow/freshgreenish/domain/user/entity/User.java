
package com.sidenow.freshgreenish.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long UserId;

    @Column(length = 20)
    private String email;

    @Column(length = 20)
    private String password;

    @Column(length = 20)
    private String nickname;

    private Integer saved_money;

    private Boolean isJoinRegular;

    @Column(length = 20)
    private String socialType;

    @Column(length = 2000)
    private String filePath;

    @Builder
    public User(String email, String password, String nickname, int saved_money, Boolean isJoinRegular, String socialType, String filePath) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.saved_money = saved_money;
        this.isJoinRegular = isJoinRegular;
        this.socialType = socialType;
        this.filePath = filePath;
    }
}