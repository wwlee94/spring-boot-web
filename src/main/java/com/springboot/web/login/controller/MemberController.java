package com.springboot.web.login.controller;

import com.springboot.web.login.register.EmailService;
import com.springboot.web.login.security.member.Member;
import com.springboot.web.login.security.member.MemberRepository;
import com.springboot.web.login.security.member.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    public ModelAndView create(Member member) {

        emailService.sendMail(member.getUemail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(member.getUpw());
        member.setUpw(passwordEncoder.encode(member.getUpw()));
        System.out.println(member.getUpw());
        member.setEamilCheck(0);

        MemberRole role = new MemberRole();
        role.setRoleName("BASIC");

        member.setRoles(Arrays.asList(role));   //member의 uid 와 join 되는 memberRole
        memberRepository.save(member);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/security/sendMail");
        mv.addObject("uemail", member.getUemail());

        return mv;
    }

    @RequestMapping("sendMail")
    public String sendMail() {


        return "/security/sendMail";
    }

    //메일 다시보내기
    @RequestMapping("reSendMail")
    public ModelAndView reSendMail(HttpServletRequest request) {

        String uemail = request.getParameter("uemail");
        emailService.sendMail(uemail);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/security/sendMail");
        mv.addObject("uemail", uemail);

        return mv;
    }


    //사용자가 받은 메일을 확인 하면 불린다.
    //사용자의 아이디값을 확인해 emailCheck 값을 변경 해준다.
    @Transactional
    @RequestMapping("emailCheck/{uemail}")
    public ModelAndView emailCheck(@PathVariable("uemail") String uemail) {
        memberRepository.updateEmailCheck(uemail);

        ModelAndView mv = new ModelAndView();
        mv.setViewName("/security/emailCheck");
        mv.addObject("uemail", uemail);

        return mv;
    }

    @RequestMapping("check")
    public ModelAndView check(HttpServletRequest request) {

        String uemail = request.getParameter("uemail");
        Member member = memberRepository.findByUemail(uemail);
        ModelAndView mv = new ModelAndView();

        //0 이라면 이메일 확인 시키기
        //1 이라면 확인 된 경우.
        if (member.getEamilCheck() == 0) {
            mv.setViewName("/security/sendMail");
            mv.addObject("uemail", uemail);
            mv.addObject("error", "메일 확인 후 사용 가능 합니다.");
        } else if (member.getEamilCheck() == 1) {
            mv.setViewName("/security/login");
            mv.addObject("uemail", uemail);
        }
        return mv;
    }

    @RequestMapping("recover")
    public String recover() {


        return "/security/recover";
    }

}