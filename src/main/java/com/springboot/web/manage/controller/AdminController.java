package com.springboot.web.manage.controller;

import com.springboot.web.login.oauth.user.User;
import com.springboot.web.login.oauth.user.UserRepository;
import com.springboot.web.login.security.member.Member;
import com.springboot.web.login.security.member.MemberRepository;
import com.springboot.web.problem.domain.Problem;
import com.springboot.web.problem.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/admin/*")
public class AdminController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProblemRepository problemRepository;


    //유저 정보 불러오기
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView userGet(HttpServletRequest request) {

        List<Member> memberList;
        List<User> userList;

        if (request.getParameter("email") != null) {
            String str = "%" + request.getParameter("email") + "%";

            memberList = memberRepository.findByUemailLike(str);
            userList = userRepository.findByEmailLike(str);
        } else {
            memberList = memberRepository.findAll();
            userList = userRepository.findAll();
        }

        memberList.forEach((item) -> {
            if (item.getEamilCheck() == 1)
                item.setEmailstr("확인");
            else
                item.setEmailstr("미확인");
        });

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/user");
        mv.addObject("memberList", memberList);
        mv.addObject("userList", userList);

        return mv;
    }

    //유저 정보 삭제
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ModelAndView userDelete(@PathVariable("id") long id) {
        memberRepository.deleteById(id);


        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/user");

        return mv;
    }

    //Oauth 유저 삭제
    @RequestMapping(value = "/Oauth/{id}", method = RequestMethod.DELETE)
    public ModelAndView OauthDelete(@PathVariable("id") long id) {
        userRepository.deleteById(id);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/user");

        return mv;
    }

    //유저 정보 불러오기
    @RequestMapping(value = "/userManage", method = RequestMethod.GET)
    public ModelAndView userManageGet(HttpServletRequest request) {

        List<Member> memberList;
        List<User> userList;

        if (request.getParameter("email") != null) {
            String str = "%" + request.getParameter("email") + "%";

            memberList = memberRepository.findByUemailLike(str);
            userList = userRepository.findByEmailLike(str);
        } else {
            memberList = memberRepository.findAll();
            userList = userRepository.findAll();
        }

        memberList.forEach((item) -> {
            item.getRoles().forEach((item2) -> {
                if (item2.getRoleName().equals("ADMIN")) {
                    item.setLevel1(true);
                } else if (item2.getRoleName().equals("MANAGER")) {
                    item.setLevel2(true);
                } else if (item2.getRoleName().equals("BASIC")) {
                    item.setLevel3(true);
                }
            });
        });

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/userManage");
        mv.addObject("memberList", memberList);
        mv.addObject("userList", userList);

        return mv;
    }


    //문제 정보 불러오기
    @RequestMapping(value = "/problem", method = RequestMethod.GET)
    public ModelAndView problem() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/problem");

        return mv;
    }

    //문제 추가하기
    @RequestMapping(value = "/problem", method = RequestMethod.POST)
    public ModelAndView problemAdd(Problem problem) {

        System.out.println(problem.getProName());
        System.out.println(problem.getProContents());
        System.out.println(problem.getProInput());
        System.out.println(problem.getProOutput());

        problemRepository.save(problem);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/admin/problem");

        return mv;
    }
}
