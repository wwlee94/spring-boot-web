package com.springboot.web.login.oauth.social;


import com.springboot.web.login.oauth.social.userconnection.UserConnection;
import com.springboot.web.login.oauth.user.User;
import com.springboot.web.login.oauth.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
@AllArgsConstructor
public class SocialService {

    @Autowired
    private final UserService userService;

    public UsernamePasswordAuthenticationToken doAuthentication(UserConnection userConnection) {

        //데이터베이스에서 회원 존재 유무를 확인 후 기존 회원일 경우는 바로 인증 회원 정보를 리턴합니다.
        //그렇지 않고 신규 회원일 경우에는 회원 가입을 진행 시킵니다.
        //이때 user_conncection, user 테이블에 두 곳 모두 저장시킵니다.

        if (userService.isExistUser(userConnection)) {
            // 기존 회원일 경우에는 데이터베이스에서 조회해서 인증 처리
            final User user = userService.findBySocial(userConnection);
            return setAuthenticationToken(user);
        } else {
            // 새 회원일 경우 회원가입 이후 인증 처리
            final User user = userService.signUp(userConnection);
            return setAuthenticationToken(user);

        }
    }

    //계정 세션 저장 해줍니다
    private UsernamePasswordAuthenticationToken setAuthenticationToken(User user) {
        return new UsernamePasswordAuthenticationToken(user, null, getAuthorities("ROLE_USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

}
