package com.springboot.web.login.user;

import com.springboot.web.login.social.userconnection.UserConnection;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "provider_id", referencedColumnName = "provider_id", nullable = false, updatable = false, unique = true)
    private UserConnection social;

    @Builder
    private User(String email, String nickname, UserConnection social) {
        this.email = email;
        this.nickname = nickname;
        this.social = social;
    }

    public static User signUp(UserConnection userConnection) {

        return User.builder()
                .email(userConnection.getEmail())
                .nickname(userConnection.getDisplayName())
                .social(userConnection)
                .build();

    }


}