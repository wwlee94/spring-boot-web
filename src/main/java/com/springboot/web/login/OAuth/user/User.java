package com.springboot.web.login.OAuth.user;

import com.springboot.web.login.OAuth.social.userconnection.UserConnection;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
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