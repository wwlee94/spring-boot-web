package com.springboot.web.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


//CustomUserDetailsService를 구현하면서 MemberRepository와 연동해 우리의 데이터베이스에 저장된 회원정보를 바탕으로
// 인증을 구현해야하는데 스프링 시큐리티의 User와 우리의 Member, GrantedAuthority와 우리의 MemberRole의 타입이
// 일치하지 않는 문제가 생긴다.
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return
                Optional.ofNullable(memberRepository.findByUemail(email))
                        .filter(m -> m!= null)
                        .map(m -> new SecurityMember(m)).get();
    }
    //Optional는 “존재할 수도 있지만 안 할 수도 있는 객체”, 즉, “null이 될 수도 있는 객체”을 감싸고 있는 일종의 래퍼 클래스입니다.
}
