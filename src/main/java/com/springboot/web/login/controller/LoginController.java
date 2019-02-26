package com.springboot.web.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/security")
public class LoginController {

    @RequestMapping("signUp")
    public String signUp(){
        return "security/signUp";
    }

    @RequestMapping("/login")
    public String loginForm(HttpServletRequest req) {
        String referer = req.getHeader("Referer");
        req.getSession().setAttribute("prevPage", referer);

        return "security/login";
    }
//
//    @PostMapping("/UserSearch")
//    public String UserSearch (HttpServletRequest req){
//        req.get
//
//
//        return null;
//    }


}
