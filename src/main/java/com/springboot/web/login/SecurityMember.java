package com.springboot.web.login;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.List;

public class SecurityMember extends User {

    //security에서 member의 앞에는 ROLE_ 를 붙여 주어야한다.
    private static final String ROLE_PREFIX = "ROLE_";
    private static final long serialVersionUID = 1L;

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public SecurityMember(Member member) {
        super(member.getUid(), member.getUpw(), makeGrantedAuthority(member.getRoles()));
    }

    private static List<GrantedAuthority> makeGrantedAuthority(List<MemberRole> roles){
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRoleName())));
        return list;
    }

    public static String getRolePrefix() {
        return ROLE_PREFIX;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
