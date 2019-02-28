package com.springboot.web.login.controller;

import com.springboot.web.login.register.EmailService;
import com.springboot.web.login.security.member.MemberRole;
import com.springboot.web.login.security.member.Member;
import com.springboot.web.login.security.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
@RequestMapping("/member")
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EmailService emailService;

    //불리는 시점 : 회원가입이 끝나고 불린다.
    //1. 스프링 시큐리티에서 지원해주는 BCryptPasswordEncoder를 이용해 회원 비밀번호를 암호화.
    //2. MemberRole을 BASIC 으로 정의해 Member에 넣어주고 save를 했다.
    @PostMapping("")
    public String create(Member member) {

        emailService.sendMail(member.getUemail());

        MemberRole role = new MemberRole();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setUpw(passwordEncoder.encode(member.getUpw()));
        role.setRoleName("BASIC");
        member.setRoles(Arrays.asList(role));   //member의 uid 와 join 되는 memberRole
        memberRepository.save(member);
        return "redirect:/security/login";
    }

}